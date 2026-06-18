package kawaidb;

//public class Main {
//}
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to KawaiiDB!");
        System.out.println("Type EXIT to quit.");

        while (true) {

            System.out.print("kawaiidb> ");

            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("EXIT")) {
                System.out.println("Goodbye!");
                break;
            }

            System.out.println("You entered: " + command);
        }

        scanner.close();
    }
}