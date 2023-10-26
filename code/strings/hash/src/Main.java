
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Scanner f = new Scanner(System.in);

        // String S = f.next();

        Hasher hasher = new Hasher("abab");

        // Iguales
        System.out.println(hasher.query(0, 1));
        System.out.println(hasher.query(2, 3));

        // Diferentes
        System.out.println(hasher.query(0, 1));
        System.out.println(hasher.query(1, 2));

    }
}