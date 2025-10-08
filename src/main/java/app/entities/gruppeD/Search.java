// Package
package dk.project;

// Imports
import java.sql.Timestamp;

public class Search {

    // Attributes
    private int id;
    private String cityName;
    private String country;
    private double latitude;
    private double longitude;
    private Timestamp createdAt;
    private boolean isCountry;

    // __________________________________________________________

    public Search(int id, String cityName, String country, double latitude, double longitude, Timestamp createdAt, boolean isCountry) {
        this.id = id;
        this.cityName = cityName;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = createdAt;
        this.isCountry = isCountry;
    }

    // __________________________________________________________

    public int getId() {
        return id;
    }

    // __________________________________________________________

    public void setId(int id) {
        this.id = id;
    }

    // __________________________________________________________

    public String getCityName() {
        return cityName;
    }

    // __________________________________________________________

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    // __________________________________________________________

    public String getCountry() {
        return country;
    }

    // __________________________________________________________

    public void setCountry(String country) {
        this.country = country;
    }

    // __________________________________________________________

    public double getLatitude() {
        return latitude;
    }

    // __________________________________________________________

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    // __________________________________________________________

    public double getLongitude() {
        return longitude;
    }

    // __________________________________________________________

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // __________________________________________________________

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    // __________________________________________________________

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // __________________________________________________________

    public boolean isCountry() {
        return isCountry;
    }

    // __________________________________________________________

    public void setCountry(boolean isCountry) {
        this.isCountry = isCountry;
    }

} // Search end