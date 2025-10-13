package app.entities.references;

public class Themes {
    private int id;
    private String name;
    private int book_id;
    private String description;

    public Themes(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Themes(int id, String name, int book_id, String description) {
        this.id = id;
        this.name = name;
        this.book_id = book_id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
