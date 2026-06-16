package wk4;

import java.util.List;

public class Examples {
    // O(n)
    public static String longestString(List<String> words) {
        String longest = "";
        for (String word : words) {
            if (word.length() > longest.length()) {
                longest = word;
            }
        }
        return longest;
    }

    // O(n^2)
    public static int countMatches(int[] nums) {
        int numberOfMatches = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    numberOfMatches++;
                }
            }
        }
        return numberOfMatches;
    }

    // O(n)
    public static boolean found(List<String> words, String target) {
        return words.contains(target);
    }
}
