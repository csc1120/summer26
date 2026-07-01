/*
 * Course: CSC-1120
 * Assignment name
 * File name
 * Name: Sean Jones
 * Last Updated:
 */
package wk6;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Simple Objects.equals examples
 */
public class ObjectsEquals {
    static void main() {
        Integer a = 5;
        Integer b = 6;
        Integer c = null;
        // equals
        // same memory location - always true
        // if b is null - always false
        // if b is same type as a - if not, false
        // compare values

        System.out.println(a.equals(c));
        if(c != null) {
            System.out.println(c.equals(a));
        }

        Objects.equals(a, b);
        List<Integer> list = new ArrayList<>();
        List<Integer> list2 = new LinkedList<>();
        System.out.println(list.getClass().getName());
        System.out.println(list2.getClass().getName());
    }
}
