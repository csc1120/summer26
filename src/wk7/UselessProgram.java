/*
 * Course: CSC-1120
 * Assignment name
 * File name
 * Name: Sean Jones
 * Last Updated:
 */
package wk7;

/**
 * Useless program to illustrate the program stack
 */
public class UselessProgram {
    static void main() {
        System.out.println(four());
    }

    private static String four() {
        return two(4);
    }

    private static String two(int a) {
        return result(a, 2);
    }

    private static String result(int a, int b) {
        StringBuilder sb = new StringBuilder();
        sb.append(doMath(a, b, Integer::sum)).append("\n");
        sb.append(doMath(a, b, (c, d) -> c - d)).append("\n");
        sb.append(doMath(a, b, (c, d) -> c * d)).append("\n");
        sb.append(doMath(a, b, (c, d) -> c / d)).append("\n");
        sb.append(doMath(a, b, (c, d) -> c % d)).append("\n");
        return sb.toString();
    }

    private static int doMath(int a, int b, Math m) {
        return m.apply(a, b);
    }

    @FunctionalInterface
    private interface Math {
        int apply(int a, int b);
    }
}
