import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    private static final String url = "jdbc:mysql://localhost:3306/book_management_system";
    private static final String userName = "root";
    private static final String password = "positive";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url, userName, password);
            Statement statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        AuthorManager authorManager = new AuthorManager(scanner);
        BookManager bookManager = new BookManager(authorManager, scanner);
        while (true) {
            System.out.println("---Welcome to Book Management System---");
            System.out.println("1. Manage Authors");
            System.out.println("2. Manage Books");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {
                    System.out.println("Entering Author Management... \n");
                    authorManager.manageAuthors(bookManager);
                    System.out.println("Returned to Main Menu from Author Management. \n");
                }
                case 2 -> {
                    System.out.println("Entering Book Management... \n");
                    bookManager.manageBooks();
                    System.out.println("Returned to Main Menu from Book Management. \n");
                }
                case 3 -> {
                    System.out.println("Exiting System. Goodbye! \n");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}





































