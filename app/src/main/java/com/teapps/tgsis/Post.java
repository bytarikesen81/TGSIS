package com.teapps.tgsis;

public class Post {
    private String title;
    private String description;
    private String address;

    public Post(){}

    public Post(String title, String description, String address) {
        this.title = title;
        this.description = description;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
