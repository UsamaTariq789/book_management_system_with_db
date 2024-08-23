import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class BookManager {
    private final Scanner scanner;
    private final Connection connection;

    public BookManager(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addBook(String title, int authorId) {
        // If the author exists, proceed to add the book to the books table
        String sql = "INSERT INTO books (title, author_id) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, authorId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book Added Successfully! \n");
            } else {
                System.out.println("Author not found. \n");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public void deleteBook(int id) {
        String deleteBookQuery = "DELETE FROM books WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteBookQuery)) {
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book Deleted Successfully! \n");
            } else {
                System.out.println("Book not found.\n");
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public void updateBook(int id, String title, int authorId) {
        String updateBookQuery = "UPDATE books SET title = ?, author_id = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateBookQuery)) {
            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, authorId);
            preparedStatement.setInt(2, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book Deleted Successfully! \n");
            } else {
                System.out.println("Book not found.\n");
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public void viewBooks() {

        String viewBooksQuery = "SELECT * FROM books";

        try (PreparedStatement preparedStatement = connection.prepareStatement(viewBooksQuery)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            boolean booksFound = false;

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int authorId = resultSet.getInt("author_id");

                // Assuming Book has a constructor that takes id, title, and authorId
                Book book = new Book(id, title, authorId);

                System.out.println(book.toString());
                booksFound = true; // Set flag to true since at least one book is found
            }

            if (!booksFound) {
                System.out.println("No books found.\n");
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public void searchBookByTitle(String title) {
        String searchQuery = "SELECT * FROM books WHERE LOWER(title) LIKE ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {
            preparedStatement.setString(1, "%" + title.toLowerCase() + "%"); // Use wildcards for partial matching

            ResultSet resultSet = preparedStatement.executeQuery();

            boolean booksFound = false;

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String bookTitle = resultSet.getString("title");
                int authorId = resultSet.getInt("author_id");

                // Assuming Book has a constructor that takes id, title, and authorId
                Book book = new Book(id, bookTitle, authorId);

                System.out.println(book.toString());
                booksFound = true;
            }

            if (!booksFound) {
                System.out.println("No books found with the title containing: " + title);
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public void searchBooksByAuthor(String name) {

        String searchQuery = "SELECT books.title " +
                "FROM books " +
                "JOIN authors ON books.author_id = authors.id " +
                "WHERE LOWER(authors.name) LIKE ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {
            preparedStatement.setString(1, "%" + name.toLowerCase() + "%"); // Use wildcards for partial matching

            ResultSet resultSet = preparedStatement.executeQuery();

            boolean booksFound = false;

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String bookTitle = resultSet.getString("title");
                int authorId = resultSet.getInt("author_id");

                // Assuming Book has a constructor that takes id, title, and authorId
                Book book = new Book(id, bookTitle, authorId);

                System.out.println(book.toString());
                booksFound = true;
            }

            if (!booksFound) {
                System.out.println("No books found with the author name containing: " + name);
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }


    public void manageBooks() {
        while (true) {
            System.out.println("Manage Books:");
            System.out.println("1. Add Book");
            System.out.println("2. Delete Book");
            System.out.println("3. Update Book");
            System.out.println("4. View All Books");
            System.out.println("5. Search books by name");
            System.out.println("6. Search books by author");
            System.out.println("7. Back");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Book name: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Author id: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    addBook(title, id);
                }
                case 2 -> {
                    System.out.print("Enter Book id: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    deleteBook(id);
                }
                case 3 -> {
                    System.out.print("Enter Book id: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Author id: ");
                    int authorId = scanner.nextInt();
                    scanner.nextLine();
                    updateBook(id, title, authorId);
                }
                case 4 -> {
                    viewBooks();
                }
                case 5 -> {
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    searchBookByTitle(title);
                }
                case 6 -> {
                    System.out.print("Enter author name: ");
                    String name = scanner.nextLine();
                    searchBooksByAuthor(name);
                }
                case 7 -> {
                    System.out.println("Navigating back!");
                    return;
                }
                default -> {
                    System.out.println("Invalid choice.");
                }
            }
        }
    }
}