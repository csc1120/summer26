/*
 * Course: CSC-1120
 * Assignment name
 * File name
 * Name: Sean Jones
 * Last Updated:
 */
package wk7;

import wk6.SJLinkedList;

public class IteratorDrver {
    static void main() {
        SJLinkedList<Integer> list = new SJLinkedList<>();
        list.add(4);
        list.add(2);
        for(Integer i : list) { // O(n)
            System.out.println(i);
        }
        for(int i = 0; i < list.size(); ++i) { // O(n) <-- O(n^2)
            System.out.println(list.get(i)); // O(n)
        }
    }
}
