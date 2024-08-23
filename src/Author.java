public class Author {;
    private int id;
    private String name;

    Author(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //Getters

    int getId() {
        return id;
    }

    String getName() {
        return name;
    }

    //Setters
    void setId(int id) {
        this.id = id;
    }

    void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Author{" +
                "Id='" + id + '\'' +
                ", Name='" + name + '\'' +
                '}';
    }

}
