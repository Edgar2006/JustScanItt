package com.example.justscanitt.Class;

public class ProductBio {

    private String companyEmail, companyName, productName, imageRef, bio, barCode;

    @Override
    public String toString() {
        return "ProductBio{" +
                "companyEmail='" + companyEmail + '\'' +
                ", companyName='" + companyName + '\'' +
                ", productName='" + productName + '\'' +
                ", imageRef='" + imageRef + '\'' +
                ", bio='" + bio + '\'' +
                ", barCode='" + barCode + '\'' +
                '}';
    }

    public ProductBio() {
    }

    public ProductBio(String companyEmail, String companyName, String productName, String imageRef, String bio, String barCode) {
        this.companyEmail = companyEmail;
        this.companyName = companyName;
        this.productName = productName;
        this.imageRef = imageRef;
        this.bio = bio;
        this.barCode = barCode;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageRef() {
        return imageRef;
    }

    public void setImageRef(String imageRef) {
        this.imageRef = imageRef;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

}
