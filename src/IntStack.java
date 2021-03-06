/*
    Name: Jonathan Tran
    PID:  A15967290
 */

import java.util.EmptyStackException;

/**
 * IntStack is created to replicate integer array. It is used as a stack
 * for integers using the first in last out method.
 * @author Jonathan Tran
 * @since  10/11/21
 */
public class IntStack {

    /* instance variables, feel free to add more if you need */
    private int[] data;
    private int nElems;
    private double loadFactor;
    private double shrinkFactor;
    private int totalCapacity;
    private int origSize;

    private static final double MAX_L = 1;
    private static final double MIN_L = 0.67;
    private static final double MAX_S = 0.33;
    private static final double MIN_S = 0;
    private static final double MIN_CAP = 5;
    private static final double SHRINK_F = .25;
    private static final double LOAD_F = .75;
    private static final int DOUBLE = 2;

    /**
     * This constructor sets the capacity, load factor, and shrink factor of
     * the IntStack to the parameters.
     * @param capacity how much the array can hold
     * @param loadF determines when the stack needs to be increased
     * @param shrinkF determines when the stack needs to be decreased
     */
    public IntStack(int capacity, double loadF, double shrinkF) {
        if (capacity < MIN_CAP || loadF < MIN_L || loadF > MAX_L
                || shrinkF <= MIN_S || shrinkF > MAX_S) {
            throw new IllegalArgumentException();
        }
        this.totalCapacity = capacity;
        this.loadFactor = loadF;
        this.shrinkFactor = shrinkF;
        this.data = new int[capacity];
        this.origSize = capacity;
        this.nElems = 0;
    }

    /**
     * This constructor sets the capacity, load factor, and shrink factor of
     * the IntStack to the parameters. The ShrinkF is set to .25
     * @param capacity how much the array can hold
     * @param loadF determines when the stack needs to be increased
     */
    public IntStack(int capacity, double loadF) {
        this(capacity, loadF, SHRINK_F);
        if (capacity < MIN_CAP || loadF < MIN_L || loadF > MAX_L) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * This constructor sets the capacity, load factor, and shrink factor of
     * the IntStack to the parameters. The ShrinkF is set to .25 and the
     * loadF is set to .75
     * @param capacity how much the array can hold
     */
    public IntStack(int capacity) {
        this(capacity, LOAD_F, SHRINK_F);
        if (capacity < MIN_CAP) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * This method checks if the stack is empty
     * @return boolean stating true if it is empty, false if not
     */
    public boolean isEmpty() {
        if (nElems == 0) {
            return true;
        }
        return false;
    }

    /**
     * This method clears the stack and sets the capacity to the
     * original size
     */
    public void clear() {
        this.nElems = 0;
        this.data = new int[this.origSize];
        this.totalCapacity = this.origSize;
    }

    /**
     * This method returns the size of the stack
     * @return number of elements
     */
    public int size() {
        return this.nElems;
    }

    /**
     * This method returns the capacity of the stack
     * @return an int stating the totalCapacity
     */
    public int capacity() {
        return this.totalCapacity;
    }

    /**
     * This method returns the top element
     * @return a string of the top element
     * @throws EmptyStackException when the stack is empty
     */
    public int peek() {
        if (nElems == 0) {
            throw new EmptyStackException();
        }
        return this.data[this.nElems - 1];
    }

    /**
     * This method pushes the element into the stack
     * @param element a string
     */
    public void push(int element) {
        // checks load factor
        double temp = (double) this.nElems / this.totalCapacity;
        if (temp >= this.loadFactor) {
            this.totalCapacity = DOUBLE * this.totalCapacity;
            int[] temp1 = new int[totalCapacity];
            // increase size of array with same elements
            for (int i = 0; i < this.nElems; i++) {
                temp1[i] = this.data[i];
            }
            this.data = temp1;
        }
        this.data[this.nElems] = element;
        this.nElems++;
    }

    /**
     * This method pops the last element from the stack
     * @return a string showing the last element
     * @throws EmptyStackException when the stack is empty
     */
    public int pop() {
        if (nElems == 0) {
            throw new EmptyStackException();
        }
        this.nElems--;
        int lastElem = this.data[this.nElems];
        // decrease capacity if load factor is less than or
        // equal to shrink factor
        if (((double) nElems / this.totalCapacity) <= this.shrinkFactor) {
            this.totalCapacity = this.totalCapacity / DOUBLE;
            // capacity goes back to original if it is less than it
            if (this.totalCapacity < this.origSize) {
                this.totalCapacity = this.origSize;
            }
            int[] newOne = new int[this.totalCapacity];
            for (int i = 0; i < this.nElems; i++) {
                newOne[i] = this.data[i];
            }
            this.data = newOne;
        }
        return lastElem;
    }

    /**
     * This method pushes multiple elements into the stack
     * @throws IllegalArgumentException when elements are null
     */
    public void multiPush(int[] elements) {
        if (elements == null) {
            throw new IllegalArgumentException();
        }
        for (int elem : elements) {
            push(elem);
        }
    }

    /**
     * This method pops multiple elements from the stack
     * @return a string array containing the popped elements
     * @throws IllegalArgumentException when amount is not positive
     */
    public int[] multiPop(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException();
        }
        // if there aren't enough elements, pop all
        if (this.nElems < amount) {
            amount = this.nElems;
        }
        int[] newData = new int[amount];
        for (int i = 0; i < amount; i++) {
            newData[i] = pop();
            }
        return newData;
    }
}
