package com.dveamer.simplifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SimplifierFactory {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Simplifier create(List<String> paths) {
        long start = System.currentTimeMillis();

        Analyst analyst = new SingleAnalyst();
        analyst.analysis(paths);
        Simplifier simplifier = createSimplifier(analyst);

        logger.debug("elapsed time : {} ms", System.currentTimeMillis() - start);
        return simplifier;
    }

    private UrlSimplifier createSimplifier(Analyst analyst) {
        return new UrlSimplifier(analyst.getWordSet(),analyst.getFullPathMap(), analyst.getLastPieceMap(), analyst.getSubPathSet());
    }
}
