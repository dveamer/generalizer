package com.dveamer.simplifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Analyst {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Set<String> wordSet = ConcurrentHashMap.newKeySet();
    private Map<String, FullPath> fullPathMap = new ConcurrentHashMap<>();
    private Map<SubPath, FullPath> lastPieceMap = new ConcurrentHashMap<>();
    private Set<SubPath> subPathSet = ConcurrentHashMap.newKeySet();

    void analysis(List<String> paths) {

        long start = System.currentTimeMillis();

        if(paths.size()>10000) {
            paths.parallelStream().forEach(path-> analysis(path));
            logger.debug("elapsed time : {} ms", System.currentTimeMillis() - start);
            return;
        }
        paths.stream().forEach(path-> analysis(path));
        logger.debug("elapsed time : {} ms", System.currentTimeMillis() - start);
    }


    void analysis(String path) {
        FullPath fullPath = FullPath.create(path);
        storeFullPath(fullPath);
        if(fullPath.hasNoVariable()) {
            return;
        }
        storeWords(fullPath);

        storePieces(fullPath);
    }

    private void storeWords(FullPath fullPath) {
        wordSet.addAll(extractWords(fullPath));
    }

    private Set<String> extractWords(FullPath fullPath) {
        List<String> words = Arrays.asList(fullPath.getSearchableFullPath().split(Constants.SYMBOL_PATH_DELIMITER));
        Set<String> wordSet = words.stream()
                .filter(s->!s.startsWith(Constants.SYMBOL_VARIABLE_START))
                .collect(Collectors.toSet());

        if(hasNoExtension(words)) {
            return wordSet;
        }

        String lastWordWithExtension = words.get(words.size()-1);
        int dotPosition = lastWordWithExtension.lastIndexOf(Constants.SYMBOL_DOT);
        String lastWord = lastWordWithExtension.substring(0, dotPosition);
        String extension = lastWordWithExtension.substring(dotPosition+1);

        if(!lastWord.startsWith(Constants.SYMBOL_VARIABLE_START)) {
            wordSet.add(lastWord);
        }

        if(!extension.startsWith(Constants.SYMBOL_VARIABLE_START)) {
            wordSet.add(Constants.SYMBOL_DOT + extension);
        }

        return wordSet;
    }

    private boolean hasNoExtension(List<String> words) {
        return !words.get(words.size()-1).contains(Constants.SYMBOL_DOT);
    }

    private void storeFullPath(FullPath fullPath) {
        fullPathMap.put(fullPath.getSearchableFullPath(), fullPath);
    }

    private void storePieces(FullPath fullPath) {
        List<String> words = Arrays.asList(fullPath.getSearchableFullPath().split(Constants.SYMBOL_PATH_DELIMITER));

        List<SubPath> piecesList = SubPath.createList(words);
        subPathSet.addAll(piecesList);
        lastPieceMap.put(piecesList.get(piecesList.size()-1), fullPath);
    }

    Set<String> getWordSet() {
        return Collections.unmodifiableSet(new HashSet<>(wordSet));
    }

    Map<String, FullPath> getFullPathMap() {
        return Collections.unmodifiableMap(new HashMap<>(fullPathMap));
    }

    Map<SubPath, FullPath> getLastPieceMap() {
        return Collections.unmodifiableMap(new HashMap<>(lastPieceMap));
    }

    Set<SubPath> getSubPathSet() {
        return Collections.unmodifiableSet(new HashSet<>(subPathSet));
    }
}
