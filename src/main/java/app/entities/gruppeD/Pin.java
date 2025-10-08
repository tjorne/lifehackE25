// Package
package dk.project;

// Imports
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Pin {

    // Attributes
    private int id;
    private int user_id;
    private int category_id;
    private double latitude;
    private double longitude;
    private Timestamp created_at;
    private String title;
    private int rating;

    // ______________________________________________________

    public Pin(int id, int user_id, int category_id, double latitude, double longitude, Timestamp created_at, String tile, int rating) {
        this.id = id;
        this.user_id = user_id;
        this.category_id = category_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.created_at = created_at;
        this.title = tile;
        this.rating = rating;
    }

    // ______________________________________________________

    public int getId() {
        return id;
    }

    // ______________________________________________________

    public void setId(int id) {
        this.id = id;
    }

    // ______________________________________________________

    public int getUser_id() {
        return user_id;
    }

    // ______________________________________________________

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    // ______________________________________________________

    public int getCategory_id() {
        return category_id;
    }

    // ______________________________________________________

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    // ______________________________________________________

    public double getLatitude() {
        return latitude;
    }

    // ______________________________________________________

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    // ______________________________________________________

    public double getLongitude() {
        return longitude;
    }

    // ______________________________________________________

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // ______________________________________________________

    public Timestamp getCreated_at() {
        return created_at;
    }

    // ______________________________________________________

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    // ______________________________________________________

    public String getTitle() {
        return title;
    }

    // ______________________________________________________

    public void setTitle(String title) {
        this.title = title;
    }

    // ______________________________________________________

    public int getRating() {
        return rating;
    }

    // ______________________________________________________

    public void setRating(int rating) {
        this.rating = rating;
    }

} // Pin end