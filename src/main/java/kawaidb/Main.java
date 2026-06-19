package kawaidb;

//public class Main {
//}


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Database db = new Database();


        db.loadAllTables();
        System.out.println("Welcome to KawaiiDB!");

        while (true) {

            System.out.print("kawaiidb> ");

            String sql = scanner.nextLine();

            if (sql.equalsIgnoreCase("EXIT")) {
                break;
            }

            SqlParser.execute(sql, db);
        }

        scanner.close();
    }
}