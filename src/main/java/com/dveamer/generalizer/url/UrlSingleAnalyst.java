package com.dveamer.generalizer.url;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

class UrlSingleAnalyst extends UrlAbstractAnalyst {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    UrlSingleAnalyst() {
        super(
            new HashSet<>()
            ,new HashMap<>()
            ,new HashMap<>()
            ,new HashSet<>()
        );
    }

    public void analysis(List<String> paths) {
        long start = System.currentTimeMillis();
        for(String path : paths) {
            analysis(path);
        }
        logger.debug("elapsed time : {} ms", System.currentTimeMillis() - start);
    }

    @Override
    public Set<String> getWordSet() {
        return Collections.unmodifiableSet(wordSet);
    }

    @Override
    public Map<String, FullPath> getFullPathMap() {
        return Collections.unmodifiableMap(fullPathMap);
    }

    @Override
    public Map<SubPath, FullPath> getLastPieceMap() {
        return Collections.unmodifiableMap(lastPieceMap);
    }

    @Override
    public Set<SubPath> getSubPathSet() {
        return Collections.unmodifiableSet(subPathSet);
    }
}
