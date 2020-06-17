package com.dveamer.generalizer;

public interface Generalizer<T, R> {
    R simplify(T path);
}
