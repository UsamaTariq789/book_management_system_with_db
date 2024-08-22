import java.util.ArrayList;
import java.util.Scanner;

public class BookManager {
    private ArrayList<Book> books = new ArrayList<>();
    private final AuthorManager authorManager;
    private final Scanner scanner;

    public BookManager(AuthorManager authorManager, Scanner scanner) {
        this.authorManager = authorManager;
        this.scanner = scanner;
    }

    public void addBook(String title, int authorId) {
        Author author = authorManager.getAuthorById(authorId);

        if (author == null) {
            System.out.println("Invalid author ID.");
            return;
        }
        books.add(
                new Book(title, authorId)
        );
        System.out.println("Book Added Successfully! \n");
    }

    public void deleteBook(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                books.remove(book);
                System.out.println("Book Deleted Successfully! \n");
                return;
            }
        }
        System.out.println("Book not found.\n");
    }

    public void deleteBooksByAuthorId(int authorId) {
        books.removeIf(book -> book.getAuthorId() == authorId);
        System.out.println("All books by the author with ID " + authorId + " have been deleted.\n");
    }

    public void updateBook(int id, String title, int authorId) {
        Author author = authorManager.getAuthorById(authorId);
        if (author == null) {
            System.out.println("Invalid author ID.");
            return;
        }
        for (Book book : books) {
            if (book.getId() == id) {
                book.setTitle(title);
                book.setAuthorId(authorId);
                System.out.println("Book updated successfully! \n");
                return;
            }
        }
        System.out.println("Book not found.\n");
    }

    public void viewBooks() {
        if (!books.isEmpty()) {
            for (Book book : books) {
                System.out.println(book.toString());
            }
        } else {
            System.out.println("Book not found.\n");
        }
    }

    public void searchBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title)) {
                System.out.println(book.toString());
            }
        }
    }

    public void searchBooksByAuthor(String name) {
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(name)) {
                System.out.println(book.toString());
            }
        }
    }

    public Book getBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
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
                    searchBookByTitle(name);
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