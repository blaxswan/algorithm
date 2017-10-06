package com.blaxswan.algorithm.heap;

public interface BinaryHeap<T extends  Comparable<T>> extends IHeap<T> {

    enum HeapType{
        Tree,
        Array
    }

    enum OrderingType{
        MIN,
        MAX
    }

    T[] getHeap();
}
