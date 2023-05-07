package com.teapps.tgsis;

import android.media.Image;
import android.widget.ImageView;

public class UserPhoto extends UserMedia {
    private Image content;
    public UserPhoto(String title, int size, Image content) {
        super(title,size);
        this.content = content;
    }

    public Image getContent() {
        return content;
    }

    public void setContent(Image content) {
        this.content = content;
    }
}
