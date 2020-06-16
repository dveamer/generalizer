package com.dveamer.simplifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractAnalyst implements Analyst{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    final Set<String> wordSet;
    final Map<String, FullPath> fullPathMap;
    final Map<SubPath, FullPath> lastPieceMap;
    final Set<SubPath> subPathSet;

    AbstractAnalyst(Set<String> wordSet, Map<String, FullPath> fullPathMap, Map<SubPath, FullPath> lastPieceMap, Set<SubPath> subPathSet) {
        this.wordSet = wordSet;
        this.fullPathMap = fullPathMap;
        this.lastPieceMap = lastPieceMap;
        this.subPathSet = subPathSet;
    }

    void analysis(String path) {
        FullPath fullPath = FullPath.create(path);
        storeFullPath(fullPath);
        storeWords(fullPath);
        if(fullPath.hasNoVariable()) {
            return;
        }
        storePieces(fullPath);
    }

    void storeWords(FullPath fullPath) {
        wordSet.addAll(extractWords(fullPath));
    }

    Set<String> extractWords(FullPath fullPath) {
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

    boolean hasNoExtension(List<String> words) {
        return !words.get(words.size()-1).contains(Constants.SYMBOL_DOT);
    }

    void storeFullPath(FullPath fullPath) {
        fullPathMap.put(fullPath.getSearchableFullPath(), fullPath);
    }

    void storePieces(FullPath fullPath) {
        List<String> words = Arrays.asList(fullPath.getSearchableFullPath().split(Constants.SYMBOL_PATH_DELIMITER));

        List<SubPath> piecesList = SubPath.createList(words);
        subPathSet.addAll(piecesList);
        lastPieceMap.put(piecesList.get(piecesList.size()-1), fullPath);
    }

}
