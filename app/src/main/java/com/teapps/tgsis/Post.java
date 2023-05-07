package com.teapps.tgsis;

import com.google.firebase.Timestamp;
public class Post {
    private int ID;
    private User author;
    private String title;
    private String content;
    private String expirationDate;

    public Post(User author, String title, String content, String expirationDate) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.expirationDate = expirationDate;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

}
