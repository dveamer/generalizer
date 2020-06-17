package com.dveamer.generalizer.url;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Deprecated
class UrlParallelAnalyst extends UrlAbstractAnalyst {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    UrlParallelAnalyst() {
        super(
            ConcurrentHashMap.newKeySet()
            ,new ConcurrentHashMap<>()
            ,new ConcurrentHashMap<>()
            ,ConcurrentHashMap.newKeySet()
        );
    }

    public void analysis(List<String> paths) {
        long start = System.currentTimeMillis();
        paths.parallelStream().forEach(path-> analysis(path));
        logger.debug("elapsed time : {} ms", System.currentTimeMillis() - start);
    }

    @Override
    public Set<String> getWordSet() {
        return Collections.unmodifiableSet(new HashSet<>(wordSet));
    }

    @Override
    public Map<String, FullPath> getFullPathMap() {
        return Collections.unmodifiableMap(new HashMap<>(fullPathMap));
    }

    @Override
    public Map<SubPath, FullPath> getLastPieceMap() {
        return Collections.unmodifiableMap(new HashMap<>(lastPieceMap));
    }

    @Override
    public Set<SubPath> getSubPathSet() {
        return Collections.unmodifiableSet(new HashSet<>(subPathSet));
    }
}
