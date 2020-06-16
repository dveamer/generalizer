package com.dveamer.simplifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Deprecated
public class ParallelAnalyst extends AbstractAnalyst{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    ParallelAnalyst() {
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
