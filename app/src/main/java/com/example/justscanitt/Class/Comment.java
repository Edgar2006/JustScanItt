package com.example.justscanitt.Class;


public class Comment {

    private String email, name, comment, imageRef;
    private float ratingBarScore;

    public Comment(String email, String name, String comment, String imageRef, float ratingBarScore) {
        this.email = email;
        this.name = name;
        this.comment = comment;
        this.imageRef = imageRef;
        this.ratingBarScore = ratingBarScore;
    }

    public Comment() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImageRef() {
        return imageRef;
    }

    public void setImageRef(String imageRef) {
        this.imageRef = imageRef;
    }

    public float getRatingBarScore() {
        return ratingBarScore;
    }

    public void setRatingBarScore(float ratingBarScore) {
        this.ratingBarScore = ratingBarScore;
    }
}
