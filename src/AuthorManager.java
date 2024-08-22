import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AuthorManager {
    private List<Author> authors = new ArrayList<>();
    private final Scanner scanner;

    AuthorManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public void addAuthor(String name) {
        authors.add(
                new Author(name)
        );
        System.out.println("Author Added Successfully! \n");
    }

    public void deleteAuthor(int id, BookManager bookManager) {
        for (Author author : authors) {
            if (author.getId() == id) {
                while (true) {
                    System.out.println("Deleting Author will also delete its books. Choose Option.");
                    System.out.println("1.Delete");
                    System.out.println("2.Cancel");

                    int choice = Integer.parseInt(scanner.nextLine());

                    switch (choice) {
                        case 1 -> {
                            authors.remove(author);
                            bookManager.deleteBooksByAuthorId(id);
                            System.out.println("Author Deleted Successfully! \n");
                            return;
                        }
                        case 2 -> {
                            return;
                        }
                    }
                }
            }
        }
        System.out.println("Author not found.\n");
    }

    public void updateAuthor(int id, String name) {
        for (Author author : authors) {
            if (author.getId() == id) {
                author.setName(name);
                System.out.println("Author updated successfully! \n");
                return;
            }
        }
        System.out.println("Author not found.\n");
    }

    public void viewAuthors() {
        if (!authors.isEmpty()) {
            for (Author author : authors) {
                System.out.println(author.toString());
                System.out.println();
            }
        } else {
            System.out.println("Author not found.\n");
        }
    }

    public void searchAuthorByName(String name) {
        for (Author author : authors) {
            if (author.getName().toLowerCase().contains(name)) {
                System.out.println(author.toString());
            }
        }
    }

    public Author getAuthorById(int id) {
        for (Author author : authors) {
            if (author.getId() == id) {
                return author;
            }
        }
        return null;
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
                    deleteAuthor(id, bookManager);
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
