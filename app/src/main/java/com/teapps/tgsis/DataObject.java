package com.teapps.tgsis;

import com.google.firebase.Timestamp;

public class DataObject {
    private String title;
    private String content;
    private Timestamp ts;

    public DataObject(String title, String content, Timestamp ts) {
        this.title = title;
        this.content = content;
        this.ts = ts;
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

    public Timestamp getTs() {
        return ts;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }
}
