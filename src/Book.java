public class Book {
    private int id;
    private String title;
    private int authorId;


    public Book(int id, String title, int authorId) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
    }
    // Getters

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getAuthorId() {
        return authorId;
    }

    //Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "Id='" + id + '\'' +
                ", Title='" + title + '\'' +
                ", AuthorId='" + authorId + '\'' +
                '}';
    }

}