// Package
package app.helper;

// Imports

public class ErrorResponse {

    // Attributes
    private final String message;
    private final String errorColor;

    // _______________________________________________________

    public ErrorResponse(String message, String errorColor) {
        this.message = message;
        this.errorColor = errorColor;
    }

    // _______________________________________________________

    public String getMessage() {
        return message;
    }

    // _______________________________________________________

    public String getErrorColor() {
        return errorColor;
    }

} // ErrorResponse end