package com.example.justscanitt.Class;

public class User {
    // Instance fields (for Firebase saving)
    private String name;
    private String email;


    // Static fields (for use throughout the app)
    public static String BARCODE;
    public static String NAME;
    public static String EMAIL;
    public static String EMAIL_CONVERT;

    // Empty constructor required by Firebase
    public User() {}

    // Constructor to initialize user data
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters (optional, for Firebase or internal use)
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
