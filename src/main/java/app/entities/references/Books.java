package app.entities.references;

public class Books {
    private int id;
    private String title;
    private int year;
    private int field_id;
    private String description;


    public Books(int id, String title, int year, int field_id, String description) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.field_id = field_id;
        this.description = description;
    }

    public Books( int id, String title, String description){
        this.id = id;
        this.title = title;
        this.description = description;

    }

    public Books(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getField_id() {
        return field_id;
    }

    public void setField_id(int field_id) {
        this.field_id = field_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
