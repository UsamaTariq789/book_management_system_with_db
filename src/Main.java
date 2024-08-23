import java.sql.*;
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
            createTablesIfNotExist(connection);
            Statement statement = connection.createStatement();
            Scanner scanner = new Scanner(System.in);
            AuthorManager authorManager = new AuthorManager(connection, scanner);
            BookManager bookManager = new BookManager(connection, scanner);
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createTablesIfNotExist(Connection connection) {
        try (Statement stmt = connection.createStatement()) {
            // Create authors table if it doesn't exist
            String createAuthorsTable = "CREATE TABLE IF NOT EXISTS authors ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "name VARCHAR(255) NOT NULL)";
            stmt.execute(createAuthorsTable);

            // Create books table if it doesn't exist
            String createBooksTable = "CREATE TABLE IF NOT EXISTS books ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "title VARCHAR(255) NOT NULL, "
                    + "author_id INT, "
                    + "FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE)";
            stmt.execute(createBooksTable);

            System.out.println("Tables created successfully (if they didn't already exist).");

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}





































