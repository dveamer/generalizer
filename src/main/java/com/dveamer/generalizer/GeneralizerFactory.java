package com.dveamer.generalizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GeneralizerFactory {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Generalizer create(List<String> paths) {
        long start = System.currentTimeMillis();

        Analyst analyst = new SingleAnalyst();
        analyst.analysis(paths);
        Generalizer generalizer = createSimplifier(analyst);

        logger.debug("elapsed time : {} ms", System.currentTimeMillis() - start);
        return generalizer;
    }

    private UrlGeneralizer createSimplifier(Analyst analyst) {
        return new UrlGeneralizer(analyst.getWordSet(),analyst.getFullPathMap(), analyst.getLastPieceMap(), analyst.getSubPathSet());
    }
}
