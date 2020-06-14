package com.dveamer.simplifier;

import java.util.List;

public class SimplifierFactory {

    public Simplifier create(List<String> paths) {
        Analyst analyst = new Analyst();
        analyst.analysis(paths);
        return createSimplifier(analyst);
    }

    private UrlSimplifier createSimplifier(Analyst analyst) {
        return new UrlSimplifier(analyst.getWordSet(),analyst.getFullPathMap(), analyst.getLastPieceMap(), analyst.getSubPathSet());
    }
}
