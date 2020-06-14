package com.dveamer.simplifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SubPath {

    private final String leftPath;
    private final String word;
    private final int index;
    private final int size;

    static SubPath ZERO = new SubPath();

    private SubPath() {
        this.leftPath = Constants.SYMBOL_EMPTY;
        this.word = Constants.SYMBOL_EMPTY;
        this.index = -1;
        this.size = -1;
    }

    private SubPath(String leftPath, String word, int index, int size) {
        this.leftPath = leftPath;
        this.word = word;
        this.index = index;
        this.size = size;
    }

    static SubPath create(List<String> words, int index, SubPath previousSubPath) {
        if(previousSubPath==ZERO) {
            return new SubPath(
                    Constants.SYMBOL_EMPTY,
                    words.get(index),
                    index,
                    words.size()
            );
        }

        String leftPath = Constants.SYMBOL_EMPTY;
        if(previousSubPath.leftPath != Constants.SYMBOL_EMPTY) {
            leftPath = previousSubPath.leftPath + Constants.SYMBOL_PATH_DELIMITER;
        }

        return new SubPath(
                leftPath + previousSubPath.word,
                words.get(index),
                index,
                words.size()
        );
    }

    static List<SubPath> createList(List<String> words) {
        SubPath subPath = ZERO;
        List<SubPath> subPaths = new ArrayList<>(words.size());
        for(int i=0; i< words.size(); i++) {
            subPath = create(words, i, subPath);
            subPaths.add(subPath);
        }
        return subPaths;
    }

    boolean isLast() {
        return index == size - 1;
    }

    SubPath variableSubPath() {
        return new SubPath(
                leftPath,
                Constants.SYMBOL_VARIABLE,
                index,
                size
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubPath piece = (SubPath) o;
        return index == piece.index &&
                size == piece.size &&
                leftPath.equals(piece.leftPath) &&
                word.equals(piece.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftPath, word, index, size);
    }

    @Override
    public String toString() {
        return "SubPath{" +
                "leftPath='" + leftPath + '\'' +
                ", word='" + word + '\'' +
                ", index=" + index +
                ", size=" + size +
                '}';
    }
}
