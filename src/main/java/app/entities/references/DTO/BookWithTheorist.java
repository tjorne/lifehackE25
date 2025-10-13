package app.entities.references.DTO;

public class BookWithTheorist {
    private String theoristName;
    private String title;
    private String description;

    public BookWithTheorist(String theoristName, String title, String description) {
        this.theoristName = theoristName;
        this.title = title;
        this.description = description;
    }

    public String getTheoristName() {
        return theoristName;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}