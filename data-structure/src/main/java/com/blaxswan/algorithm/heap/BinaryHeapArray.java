package com.blaxswan.algorithm.heap;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A binary heap using an array to hold the nodes.
 */
public class BinaryHeapArray<T extends Comparable<T>> implements BinaryHeap<T> {

    private static final int MINIMUM_SIZE = 1024;

    private OrderingType type = OrderingType.MIN;
    private int size = 0;
    private T[] array = (T[]) new Comparable[MINIMUM_SIZE];

    public BinaryHeapArray() {

        size = 0;
    }


    public BinaryHeapArray(OrderingType type) {
        this();
        this.type = type;
    }

    @Override
    public int size() {

        return size;
    }

    @Override
    public boolean add(T value) {
        if (size >= array.length)
            grow();
        array[size] = value;

        heapUp(size++);

        return true;
    }

    @Override
    public T remove(T value) {
        if (array.length == 0) return null;
        for (int i = 0; i < size; i++) {
            T node = array[i];
            if (node.equals(value)) return remove(i);
        }
        return null;
    }

    private T remove(int index) {
        if (index<0 || index>=size) return null;

        T t = array[index];
        array[index] = array[--size];
        array[size] = null;

        heapDown(index);

        int shrinkSize = array.length>>1;
        if (shrinkSize >= MINIMUM_SIZE && size < shrinkSize)
            shrink();

        return t;
    }

    private void heapUp(int idx) {
        int nodeIndex = idx;
        T value = this.array[nodeIndex];
        if (value==null)
            return;

        while (nodeIndex >= 0) {
            int parentIndex = getParentIndex(nodeIndex);
            if (parentIndex < 0)
                return;

            T parent = this.array[parentIndex];

            if ((type == OrderingType.MIN && value.compareTo(parent) < 0)
                    || (type == OrderingType.MAX && value.compareTo(parent) > 0)
                    ) {
                // Node is greater/lesser than parent, switch node with parent
                this.array[parentIndex] = value;
                this.array[nodeIndex] = parent;
            } else {
                return;
            }
            nodeIndex = parentIndex;
        }
    }

    private void heapDown(int index) {
        T value = this.array[index];
        if (value==null)
            return;

        int leftIndex = getLeftIndex(index);
        int rightIndex = getRightIndex(index);
        T left = (leftIndex != Integer.MIN_VALUE && leftIndex < this.size) ? this.array[leftIndex] : null;
        T right = (rightIndex != Integer.MIN_VALUE && rightIndex < this.size) ? this.array[rightIndex] : null;

        if (left == null && right == null) {
            // Nothing to do here
            return;
        }

        T nodeToMove = null;
        int nodeToMoveIndex = -1;
        if ((type == OrderingType.MIN && left != null && right != null && value.compareTo(left) > 0 && value.compareTo(right) > 0)
                || (type == OrderingType.MAX && left != null && right != null && value.compareTo(left) < 0 && value.compareTo(right) < 0)) {
            // Both children are greater/lesser than node
            if ((right!=null) &&
                    ((type == OrderingType.MIN && (right.compareTo(left) < 0)) || ((type == OrderingType.MAX && right.compareTo(left) > 0)))
                    ) {
                // Right is greater/lesser than left
                nodeToMove = right;
                nodeToMoveIndex = rightIndex;
            } else if ((left!=null) &&
                    ((type == OrderingType.MIN && left.compareTo(right) < 0) || (type == OrderingType.MAX && left.compareTo(right) > 0))
                    ) {
                // Left is greater/lesser than right
                nodeToMove = left;
                nodeToMoveIndex = leftIndex;
            } else {
                // Both children are equal, use right
                nodeToMove = right;
                nodeToMoveIndex = rightIndex;
            }
        } else if ((type == OrderingType.MIN && right != null && value.compareTo(right) > 0)
                || (type == OrderingType.MAX && right != null && value.compareTo(right) < 0)
                ) {
            // Right is greater/lesser than node
            nodeToMove = right;
            nodeToMoveIndex = rightIndex;
        } else if ((type == OrderingType.MIN && left != null && value.compareTo(left) > 0)
                || (type == OrderingType.MAX && left != null && value.compareTo(left) < 0)
                ) {
            // Left is greater/lesser than node
            nodeToMove = left;
            nodeToMoveIndex = leftIndex;
        }
        // No node to move, stop recursion
        if (nodeToMove == null)
            return;

        // Re-factor heap sub-tree
        this.array[nodeToMoveIndex] = value;
        this.array[index] = nodeToMove;

        heapDown(nodeToMoveIndex);
    }

    private static int getParentIndex(int index) {
        if (index > 0)
            return (int) Math.floor((index - 1) / 2);
        return Integer.MIN_VALUE;
    }

    private static int getLeftIndex(int index) {

        return 2 * index + 1;
    }

    private static int getRightIndex(int index) {

        return 2 * index + 2;
    }

    // Grow the array by double
    private void grow() {
        int growSize = size<<1;
        array = Arrays.copyOf(array, growSize);
    }

    // Shrink the array by half
    private void shrink() {
        int shrinkSize = array.length>>1;
        array = Arrays.copyOf(array, shrinkSize);
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public boolean contains(T value) {
        if (array.length == 0) return false;
        for (int i = 0; i < size; i++) {
            T t = array[i];
            if (t.equals(value)) return true;
        }
        return false;
    }

    @Override
    public boolean validate() {
        if (array.length == 0)
            return true;
        return validateNode(0);
    }

    private boolean validateNode(int index) {
        T value = this.array[index];
        int leftIndex = getLeftIndex(index);
        int rightIndex = getRightIndex(index);

        // We shouldn't ever have a right node without a left in a heap
        if (rightIndex != Integer.MIN_VALUE && leftIndex == Integer.MIN_VALUE) return false;

        if (leftIndex != Integer.MIN_VALUE && leftIndex < size) {
            T left = this.array[leftIndex];
            if ((type == OrderingType.MIN && value.compareTo(left) < 0)
                    || (type == OrderingType.MAX && value.compareTo(left) > 0)) {
                return validateNode(leftIndex);
            }
            return false;
        }
        if (rightIndex != Integer.MIN_VALUE && rightIndex < size) {
            T right = this.array[rightIndex];
            if ((type == OrderingType.MIN && value.compareTo(right) < 0)
                    || (type == OrderingType.MAX && value.compareTo(right) > 0)) {
                return validateNode(rightIndex);
            }
            return false;
        }

        return true;
    }

    @Override
    public T[] getHeap() {
        T[] nodes = (T[]) new Comparable[size];
        if (array.length == 0) return nodes;

        for (int i = 0; i < size; i++) {
            T node = this.array[i];
            nodes[i] = node;
        }
        return nodes;
    }

    @Override
    public T getHeadValue() {
        if (array.length == 0) return null;
        return array[0];
    }

    @Override
    public T removeHead() {
        return remove(getHeadValue());
    }


    @Override
    public java.util.Collection<T> toCollection() {

        throw new NotImplementedException();
    }

    @Override
    public String toString() {
        return HeapPrinter.getString(this);
    }

    private static class HeapPrinter {

        static <T extends Comparable<T>> String getString(BinaryHeapArray<T> tree) {
            if (tree.array.length == 0)
                return "Tree has no nodes.";

            T root = tree.array[0];
            if (root == null)
                return "Tree has no nodes.";
            return getString(tree, 0, "", true);
        }

        private static <T extends Comparable<T>> String getString(BinaryHeapArray<T> tree, int index, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            T value = tree.array[index];
            builder.append(prefix + (isTail ? "└── " : "├── ") + value + "\n");
            List<Integer> children = null;
            int leftIndex = getLeftIndex(index);
            int rightIndex = getRightIndex(index);
            if (leftIndex != Integer.MIN_VALUE || rightIndex != Integer.MIN_VALUE) {
                children = new ArrayList<Integer>(2);
                if (leftIndex != Integer.MIN_VALUE && leftIndex < tree.size) {
                    children.add(leftIndex);
                }
                if (rightIndex != Integer.MIN_VALUE && rightIndex < tree.size) {
                    children.add(rightIndex);
                }
            }
            if (children != null) {
                for (int i = 0; i < children.size() - 1; i++) {
                    builder.append(getString(tree, children.get(i), prefix + (isTail ? "    " : "│   "), false));
                }
                if (children.size() >= 1) {
                    builder.append(getString(tree, children.get(children.size() - 1), prefix
                            + (isTail ? "    " : "│   "), true));
                }
            }

            return builder.toString();
        }
    }
}


