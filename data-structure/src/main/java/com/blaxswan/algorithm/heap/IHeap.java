package com.blaxswan.algorithm.heap;

/**
 * 1) a heap is a specialized tree-based data structure
 * 2) if A is a parent node of B then key(A) is ordered with respect to key(B) with the same ordering applying across the heap.
 * 3) either the keys of parent nodes are always greater than or equal to those of the children and the highest key is in the root node (this kind of heap is called max heap)
 * 4) or the keys of parent nodes are less than or equal to those of the children (min heap).
 */
 public interface IHeap<T> {

     boolean add(T value);

     T getHeadValue();

     T removeHead();

     T remove(T value);

     void clear();

     boolean contains(T value);

     int size();

     boolean validate();

     java.util.Collection<T> toCollection();
}
