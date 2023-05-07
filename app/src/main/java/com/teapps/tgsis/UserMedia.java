package com.teapps.tgsis;

public abstract class UserMedia {
    protected String title;
    protected int size;
    public UserMedia(String title, int size) {
        this.title = title;
        this.size = size;
    }
}
