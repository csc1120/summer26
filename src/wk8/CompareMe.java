/*
 * Course: CSC-1120
 * Assignment name
 * File name
 * Name: Sean Jones
 * Last Updated:
 */
package wk8;

public class CompareMe implements Comparable<CompareMe> {
    private int value;

    public CompareMe(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(CompareMe o) {
        // if this is less than o, return a negative number
        // if this is greater than o, return a positive number
        // if this is equal to o, we return 0
        return value - o.value;
    }
}
