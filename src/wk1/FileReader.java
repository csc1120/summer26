package wk1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class FileReader {
    public static void main(String[] args) {
        System.out.println("Enter a filename");
        Scanner sc = new Scanner(System.in);
        String filename = sc.nextLine();
        try (Scanner in = new Scanner(Files.newInputStream(Path.of(filename)))) {
            in.next();
            System.out.println(in.nextInt());
        } catch (IOException e) {
            System.err.println("Unable to open file " + filename);
        }
    }
}
