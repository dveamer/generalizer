package com.dveamer.generalizer.url;

import java.util.*;
import java.util.stream.Collectors;

class SubPath {

    private final List<String> leftWords;
    private final String word;
    private final int index;
    private final int size;

    static SubPath ZERO = new SubPath();

    private SubPath() {
        this.leftWords = Collections.emptyList();
        this.word = Constants.SYMBOL_EMPTY;
        this.index = -1;
        this.size = -1;
    }

    private SubPath(List<String> leftWords, String word, int index, int size) {
        this.leftWords = leftWords;
        this.word = word;
        this.index = index;
        this.size = size;
    }

    static SubPath createSubPath(List<String> words, SubPath previousSubPath) {
        int index = previousSubPath.getIndex()+1;

        if(index == 0) {
            return new SubPath(
                    Collections.emptyList(),
                    words.get(index),
                    index,
                    words.size()
            );
        }

        List<String> leftPath = words.subList(0, index);
        leftPath.set(index-1, previousSubPath.word);
        return new SubPath(
                leftPath,
                words.get(index),
                index,
                words.size()
        );
    }

    static SubPath createCustomLastSubPath(List<String> words, SubPath lastSubPath, String customizedWord) {
        return new SubPath(
                lastSubPath.leftWords,
                customizedWord,
                lastSubPath.index,
                lastSubPath.size
        );
    }

    static List<SubPath> createList(List<String> words) {
        SubPath subPath = ZERO;
        List<SubPath> subPaths = new ArrayList<>(words.size());
        for(int i=0; i< words.size(); i++) {
            subPath = createSubPath(words, subPath);
            subPaths.add(subPath);
        }
        return subPaths;
    }

    boolean isLastNode() {
        return index == size - 1;
    }

    SubPath variableSubPath() {
        return new SubPath(
                leftWords,
                Constants.SYMBOL_VARIABLE,
                index,
                size
        );
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubPath subPath = (SubPath) o;
        return index == subPath.index &&
                size == subPath.size &&
                leftWords.equals(subPath.leftWords) &&
                word.equals(subPath.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftWords, word, index, size);
    }

    @Override
    public String toString() {
        return "SubPath{" +
                "path='" + String.join(Constants.SYMBOL_PATH_DELIMITER, leftWords) + Constants.SYMBOL_PATH_DELIMITER + word + '\'' +
                ", index=" + index +
                ", size=" + size +
                '}';
    }

}
