/*
 * Course: CSC-1120
 * Assignment name
 * File name
 * Name: Sean Jones
 * Last Updated:
 */
package wk7;

public class BinarySearch {
    static void main() {
        int[] arr = {1, 3, 4, 5, 7, 9, 11, 14, 17};
        int target = 2;
        System.out.println(binarySearch(arr, target));
    }

    private static int binarySearch(int[] arr, int target) {
        int result = -1;
        int low = 0;
        int high = arr.length - 1;
        boolean found = false;
        // search
        while(!found && low <= high) {
            int middle = (high - low) / 2;
            if(arr[middle] == target) {
                found = true;
                result = middle;
            } else {
                // keep look
                // if target is less than middle, go left
                if(target < arr[middle]) {
                    high = middle - 1;
                } else {
                    // if target is greater than middle, go right
                    low = middle + 1;
                }
            }
        }
        return result;
    }
}
