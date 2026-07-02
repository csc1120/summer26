/*
 * Course: CSC-1120
 * Assignment name
 * File name
 * Name: Sean Jones
 * Last Updated:
 */
package wk6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestSuite {
    List<Integer> list;

    // @BeforeAll - sets up before ALL the tests start running

    @BeforeEach
    void setUp() {
        list = new SJLinkedList<>();
    }

    private void fillList() {
        Integer[] nums = {7, 4, 2, 8, 5};
        for(int i = 0; i < nums.length; i++) {
            list.add(nums[i]);
        }
    }

    @Test
    void testIsEmpty() {
        // happy path - everything works and we are happy
        // boundary condition / edge case
        // use Assertions to test
        Assertions.assertTrue(list.isEmpty(), "Empty list should return true");
        list.add(1);
        Assertions.assertFalse(list.isEmpty(), "List should not be empty after added an element");
    }

    @Test
    void testAddAtIndex() {
        // Boundary Conditions:
        // 1. out of bounds
        // 2. min index value
        // 3. max index value

        // add at index 0 - empty? not empty?
        fillList();
        list.add(0, 1);
        Assertions.assertEquals(6, list.size(), "Added element should make the size 6");
        Assertions.assertEquals(1, list.get(0), "Index 0 should contain the new element");
        Assertions.assertEquals(7, list.get(1), "Index 1 should contain the previous index 0");

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, 0));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.add(7, 0));
    }

}
