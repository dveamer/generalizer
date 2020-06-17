package com.dveamer.generalizer.url;

import com.dveamer.generalizer.Generalizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UrlGeneralizerFactory {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public UrlGeneralizer create(List<String> paths) {
        long start = System.currentTimeMillis();

        Analyst analyst = new UrlSingleAnalyst();
        analyst.analysis(paths);
        UrlGeneralizer generalizer = createSimplifier(analyst);

        logger.debug("elapsed time : {} ms", System.currentTimeMillis() - start);
        return generalizer;
    }

    private UrlGeneralizer createSimplifier(Analyst analyst) {
        return new UrlGeneralizer(analyst.getWordSet(),analyst.getFullPathMap(), analyst.getLastPieceMap(), analyst.getSubPathSet());
    }
}
