package com.dveamer.simplifier;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class FullPath {

    private final String fullPath;
    private final String searchableFullPath;

    private FullPath(String fullPath, String searchableFullPath) {
        this.fullPath = fullPath;
        this.searchableFullPath = searchableFullPath;
    }

    static FullPath create(String path) {
        path = validPath(path);

        if(path.indexOf(Constants.SYMBOL_VARIABLE_START) < 0) {
            return new FullPath(path, path);
        }

        String searchableFullPath = Arrays.asList(path.split(Constants.SYMBOL_PATH_DELIMITER)).stream()
                .map(s-> makeVariableSearchable(s))
                .collect(Collectors.joining(Constants.SYMBOL_PATH_DELIMITER));
        return new FullPath(path, searchableFullPath);
    }

    String getFullPath() {
        return fullPath;
    }

    String getSearchableFullPath() {
        return searchableFullPath;
    }

    boolean hasNoVariable() {
        return fullPath.equals(searchableFullPath);
    }

    static String validPath(String path) {
        StringBuilder sbPath = new StringBuilder(path);

        if(sbPath.charAt(0) == Constants.SYMBOL_CHAR_PATH_DELIMITER) {
            sbPath.deleteCharAt(0);
        }

        int positionOfQuery = sbPath.indexOf(Constants.SYMBOL_PATH_QUERY);
        if(positionOfQuery > -1) {
            sbPath.delete(positionOfQuery, sbPath.length());
        }

        if(sbPath.charAt(sbPath.length()-1) == Constants.SYMBOL_CHAR_PATH_DELIMITER) {
            sbPath.deleteCharAt(sbPath.length()-1);
        }

        return sbPath.toString();
    }

    private static String makeVariableSearchable(String word) {
        return makeVariableSearchable(new StringBuilder(word), -1).toString();
    }

    private static StringBuilder makeVariableSearchable(StringBuilder word, int start) {
        int startPosition = word.indexOf(Constants.SYMBOL_VARIABLE_START, start);
        if(startPosition < 0) {
            return word;
        }

        int endPosition = word.indexOf(Constants.SYMBOL_VARIABLE_END, start);
        if(endPosition <= startPosition) {
            String message = String.format("endPosition(%d) should be bigger than startPosition(%d) : %s", endPosition, startPosition, word);
            throw new IllegalArgumentException(message);
        }

        word.replace(startPosition, endPosition+1, Constants.SYMBOL_VARIABLE);

        return makeVariableSearchable(word, startPosition+Constants.SYMBOL_VARIABLE.length());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullPath fullPath = (FullPath) o;
        return searchableFullPath.equals(fullPath.searchableFullPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(searchableFullPath);
    }

    @Override
    public String toString() {
        return "FullPath{" +
                "fullPath='" + fullPath + '\'' +
                ", searchableFullPath='" + searchableFullPath + '\'' +
                '}';
    }
}
