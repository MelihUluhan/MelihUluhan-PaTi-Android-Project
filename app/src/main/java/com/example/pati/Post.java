package com.example.pati;

public class Post {
    String phoneNumber;
    byte[] image;
    String address;
    String explanation;
    String sender;
    Boolean isLost;

    public Post() {

    }

    public Post(byte[] image, String explanation, String phoneNumber, String address, String sender) {
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.address = address;
        this.explanation = explanation;
        this.sender = sender;
        this.isLost = true;
    }

    public Post(byte[] image, String explanation, String address, String sender) {
        this.phoneNumber = "";
        this.image = image;
        this.address = address;
        this.explanation = explanation;
        this.sender = sender;
        this.isLost = false;
    }
}
