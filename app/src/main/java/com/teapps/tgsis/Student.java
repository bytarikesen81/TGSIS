package com.teapps.tgsis;

import android.graphics.Bitmap;
import android.icu.text.Transliterator;

public class Student {
    //Main Fields
    private String uid;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Bitmap image;
    private String phoneNumber;

    //Education Status
    private Education education;

    //Application Status
    private Status status;
    private long distance;
    private long time;
    private boolean homeStatus;

    //Paired Student
    private String pairedStudentID;

    //Active Post
    private Post activePost;

    //Constructors
    public Student() {}//Default
    public Student(String uid, String name, String surname, String email, String password, Bitmap image, Education education, Status status, boolean homeStatus) {
        this.uid = uid;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.image = image;
        this.education = education;
        this.status = status;
        this.homeStatus = homeStatus;
    }

    //Getters and setters
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isHomeStatus() {
        return homeStatus;
    }

    public void setHomeStatus(boolean homeStatus) {
        this.homeStatus = homeStatus;
    }

    public String getPairedStudentID() {
        return pairedStudentID;
    }

    public void setPairedStudentID(String pairedStudentID) {
        this.pairedStudentID = pairedStudentID;
    }

    public Post getActivePost() {
        return activePost;
    }

    public void setActivePost(Post activePost) {
        this.activePost = activePost;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
