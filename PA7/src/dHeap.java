/*
 * Name: Mert Ozer
 * PID:  A17546314
 */

import java.util.NoSuchElementException;

/**
 * Title: dHeap Description: This program creates a Heap with d branching factor
 *
 * @author Mert Ozer
 * @since 05.20.23
 *
 * @param <T> the type of elements held in this collection
 */
public class dHeap<T extends Comparable<? super T>> implements HeapInterface<T> {

    private T[] heap;   // backing array
    private int d;      // branching factor
    private int nelems; // number of elements
    private boolean isMaxHeap; // indicates whether heap is max or min
    private static final int BINARY_HEAP = 2;
    private static final int DEFAULT_SIZE = 6;
    private static final int CAPACITY_INCREASE_MULTIPLIER = 2;

    /**
     * Initializes a binary max heap with capacity = 6
     */
    @SuppressWarnings("unchecked")
    public dHeap() {
        this(BINARY_HEAP, DEFAULT_SIZE, true);
    }

    /**
     * Initializes a binary max heap with a given initial capacity.
     *
     * @param heapSize The initial capacity of the heap.
     */
    @SuppressWarnings("unchecked")
    public dHeap(int heapSize) {
        this(BINARY_HEAP, heapSize, true);
    }

    /**
     * Initializes a d-ary heap (with a given value for d), with a given initial
     * capacity.
     *
     * @param d         The number of child nodes each node in the heap should have.
     * @param heapSize  The initial capacity of the heap.
     * @param isMaxHeap indicates whether the heap should be max or min
     * @throws IllegalArgumentException if d is less than one.
     */
    @SuppressWarnings("unchecked")
    public dHeap(int d, int heapSize, boolean isMaxHeap) throws IllegalArgumentException {
        if (d < 1) {
            throw new IllegalArgumentException("d must be greater than or equal to 1");
        }

        this.heap = (T[]) new Comparable[heapSize];
        this.d = d;
        this.nelems = 0;
        this.isMaxHeap = isMaxHeap;
    }

    @Override
    public int size() {
        return nelems;
    }

    @Override
    public T remove() throws NoSuchElementException {
        if (nelems == 0) {
            throw new NoSuchElementException();
        }

        T root = heap[0];
        heap[0] = heap[nelems -1];
        nelems--;
        trickleDown(0);
        return root;
    }

    @Override
    public void add(T item) throws NullPointerException {
        if (item == null) {
            throw new NullPointerException();
        }

        // if not enough space resize the heap
        if (nelems == heap.length) {
            resize();
        }

        heap[nelems] = item;
        bubbleUp(nelems);
        nelems++;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        heap = (T[]) new Comparable[heap.length];
        nelems = 0;
    }

    @Override
    public T element() throws NoSuchElementException {
        if (nelems == 0) {
            throw new NoSuchElementException();
        }
        return  heap[0];
    }

    /**
     * Correctly tricklesDown the given element to satisfy heap property
     * @param index of the element to trickle down
     */
    private void trickleDown(int index) {
        int childIndex = getPreferredChildIndex(index);

        // loop until there is no preferred child index or the heap property is satisfied
        while (childIndex >= 0) {
            T current = heap[index];
            T child = heap[childIndex];

            boolean shouldSwap = isMaxHeap ?
                    (current.compareTo(child) < 0) : (current.compareTo(child) > 0);

            if (shouldSwap) {
                swap(index, childIndex);
                index = childIndex;
                childIndex = getPreferredChildIndex(index);
            } else {
                break;
            }
        }
    }

    /**
     * Correctly bubblesUp the given element to satisfy heap property
     * @param index of the element to bubbleUp
     */
    private void bubbleUp(int index) {
        if (index == 0) {
            return; // already at the root
        }
        int parentIndex = parent(index);
        while (parentIndex >= 0) {
            T current = heap[index];
            T parent = heap[parentIndex];

            boolean shouldSwap = isMaxHeap ?
                    (current.compareTo(parent) > 0) : (current.compareTo(parent) < 0);

            if (shouldSwap) {
                swap(index, parentIndex);
                index = parentIndex;
                parentIndex = parent(index);
            } else {
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    /**
     * Resizes the heap with multiplying the current length by CAPACITY_INCREASE_MULTIPLIER
     */
    private void resize() {
        int newCapacity = heap.length * CAPACITY_INCREASE_MULTIPLIER; // doubling the size
        T[] newHeap = (T[]) new Comparable[newCapacity];
        System.arraycopy(heap, 0, newHeap, 0, heap.length);
        heap = newHeap;
    }

    /**
     * Finds the parent index of the given child
     * @param index of the child element
     * @return the parent index given the index of child
     */
    private int parent(int index) {
        if (index == 0) {
            return -1; // root has no parent
        }
        return (index - 1) / d;
    }

    /**
     * Swap the two given elements
     * @param index1 the first element to swap
     * @param index2 the second element to swap
     */
    private void swap(int index1, int index2) {
        T temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }

    /**
     * Helper method for trickleDown to decide the preferred child based on isMaxHeap
     * @param index of the parent which we want the children indexes
     * @return the potential child index for swap
     */
    private int getPreferredChildIndex(int index) {
        int preferredChildIndex = -1;
        int startChildIndex = d * index + 1;
        int endChildIndex = Math.min(startChildIndex + d, nelems); // heap might not be full

        // loop through childIndeces and compare elements based on isMaxHeap
        for (int i = startChildIndex; i < endChildIndex; i++) {
            if (isMaxHeap) {
                if (heap[i].compareTo(heap[startChildIndex]) > 0) {
                    startChildIndex = i;
                }
            } else {
                if (heap[i].compareTo(heap[startChildIndex]) < 0) {
                    startChildIndex = i;
                }
            }
        }

        if (startChildIndex < nelems) {
            preferredChildIndex = startChildIndex;
        }
         return preferredChildIndex;

    }

    /**
     * Prints the element on the heap for testing purposes.
     */
    public void printHeap() {
        System.out.print("Heap = ");
        for (int i = 0; i < nelems; i++)
            System.out.print(heap[i] +" ");
        System.out.println();
    }
}
