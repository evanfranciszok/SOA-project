import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a message:");

        // Wait for user input
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            System.out.println("received: " + input);
        }

        scanner.close();
    }
}
