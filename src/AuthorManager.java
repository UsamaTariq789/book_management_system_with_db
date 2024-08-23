import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AuthorManager {
    private final Scanner scanner;
    private final Connection connection;

    AuthorManager(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addAuthor(String name) {

        String sql = "INSERT INTO authors (name) VALUES (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Author Added Successfully! \n");
            } else {
                System.out.println("Failed to add Author. \n");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public void deleteAuthor(int id) {

        while (true) {
            System.out.println("Deleting Author will also delete its books. Choose Option.");
            System.out.println("1.Delete");
            System.out.println("2.Cancel");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {
                    String deleteAuthorQuery = "DELETE FROM authors WHERE id = ?";

                    try (PreparedStatement preparedStatement = connection.prepareStatement(deleteAuthorQuery)) {
                        preparedStatement.setInt(1, id);

                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Author Deleted Successfully! \n");
                        } else {
                            System.out.println("Author not found.\n");
                        }
                    } catch (SQLException e) {
                        System.out.println("Database error: " + e.getMessage());
                    }
                    return;
                }
                case 2 -> {
                    return;
                }
            }
        }
    }

    public void updateAuthor(int id, String name) {

        String updateBookQuery = "UPDATE authors SET name = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateBookQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Author Updated Successfully! \n");
            } else {
                System.out.println("Author not found.\n");
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public void viewAuthors() {

        String viewAuthorsQuery = "SELECT * FROM authors";

        try (PreparedStatement preparedStatement = connection.prepareStatement(viewAuthorsQuery)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            boolean authorsFound = false;

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                // Assuming Book has a constructor that takes id, title, and authorId
                Author author = new Author(id, name);

                System.out.println(author.toString());
                authorsFound = true; // Set flag to true since at least one book is found
            }

            if (!authorsFound) {
                System.out.println("No Authors found.\n");
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public void searchAuthorByName(String authorName) {

        String searchQuery = "SELECT * FROM authors WHERE LOWER(name) LIKE ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {
            preparedStatement.setString(1, "%" + authorName.toLowerCase() + "%"); // Use wildcards for partial matching

            ResultSet resultSet = preparedStatement.executeQuery();

            boolean authorsFound = false;

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                // Assuming Book has a constructor that takes id, title, and authorId
                Author author = new Author(id, name);

                System.out.println(author.toString());
                authorsFound = true;
            }

            if (!authorsFound) {
                System.out.println("No authors found with the name containing: " + authorName);
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public void manageAuthors(BookManager bookManager) {
        while (true) {
            System.out.println("Manage Authors:");
            System.out.println("1. Add Author");
            System.out.println("2. Delete Author");
            System.out.println("3. Update Author");
            System.out.println("4. View All Authors");
            System.out.println("5. Search Authors by name");
            System.out.println("6. Back");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Author name: ");
                    String name = scanner.nextLine();
                    addAuthor(name);
                }
                case 2 -> {
                    System.out.print("Enter Author id: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    deleteAuthor(id);
                }
                case 3 -> {
                    System.out.print("Enter Author id: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Author name: ");
                    String name = scanner.nextLine();
                    updateAuthor(id, name);
                }
                case 4 -> viewAuthors();
                case 5 -> {
                    System.out.print("Enter Author name: ");
                    String name = scanner.nextLine();
                    searchAuthorByName(name);
                }
                case 6 -> {
                    System.out.println("Navigating back!");
                    return; // This should return to the main method
                }
                default -> {
                    System.out.println("Invalid choice.");
                }
            }
        }
    }
}
