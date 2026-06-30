/*
 * Course: CSC-1120
 * ASSIGNMENT
 * CLASS
 * Name: Sean Jones
 * Last Updated:
 */
package wk6;

/**
 * A review of printf specifiers and modifiers
 */
public class PrintFReview {
    static int f = 5;
    static void main() {
        System.out.println("Hello There");
        System.out.println("Hello There\\");
        System.out.printf("Hello There%n");
        System.out.printf("%s %s%n", "Hello", "There");
        System.out.printf("%-10s %s%n", "Hello", "There");
        System.out.printf("%04d %.2f", 4, 45.34723776524523);
        String s = String.format("%04d %.2f", 4, 45.34723776524523);
        String t = "hoi";

    }
}
