package com.example.upevents;

public class Myevnt {
    private String name;
    private String createur;
    private String date;
    private String time;
    private String email;
    private String  phone;
    private String userId;
    private String price;
    private String localisation;
    private String description;

    public Myevnt(String name, String createur, String date, String time, String email, String phone, String userId, String price, String localisation, String description) {
        this.name = name;
        this.createur = createur;
        this.date = date;
        this.time = time;
        this.email = email;
        this.phone = phone;
        this.userId = userId;
        this.price = price;
        this.localisation = localisation;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateur() {
        return createur;
    }

    public void setCreateur(String createur) {
        this.createur = createur;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }


    public void setPostKey(String key) {
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
