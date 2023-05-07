package com.teapps.tgsis;

import android.provider.MediaStore;

public class UserVideo extends UserMedia{
    private MediaStore.Video content;
    private int duration;

    public UserVideo(String title, int size, MediaStore.Video content, int duration) {
        super(title, size);
        this.content = content;
        this.duration = duration;
    }

    public MediaStore.Video getContent() {
        return content;
    }

    public void setContent(MediaStore.Video content) {
        this.content = content;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
