package com.dveamer.generalizer;

public interface Generalizer<T, R> {
    R generalize(T path);
}
