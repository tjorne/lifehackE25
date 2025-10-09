package app.entities.filmRouletten;

import java.sql.Date;

public class Movie {

    private int id;
    private String title;
    private String description;
    private Date timeStamp;
    private int rating;
    private int length;

    public Movie(int id, String title, String description, Date timeStamp, int rating, int length) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.timeStamp = timeStamp;
        this.rating = rating;
        this.length = length;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
