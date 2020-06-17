package com.dveamer.generalizer.url;

import java.util.List;
import java.util.Map;
import java.util.Set;

interface Analyst {

    void analysis(List<String> paths);

    Set<String> getWordSet();

    Map<String, FullPath> getFullPathMap();

    Map<SubPath, FullPath> getLastPieceMap();

    Set<SubPath> getSubPathSet();
}
