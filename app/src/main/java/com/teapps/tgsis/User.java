package com.teapps.tgsis;

import android.media.Image;
import android.provider.ContactsContract;

import com.google.type.Date;

import java.sql.Timestamp;
import java.util.ArrayList;

public class User {
    //MANDATORY DATA FIELDS
    private String name;
    private String surname;
    private String entryDate;
    private String graduationDate;
    private String mail;
    private String password;
    private Image profileImage;
    //CONSTRUCTOR
    public User() {

    }
    public User(String name, String surname, String entryDate, String graduationDate, String mail, String password) {
        this.name = name;
        this.surname = surname;
        this.entryDate = String.valueOf(entryDate);
        this.graduationDate = String.valueOf(graduationDate);
        this.mail = mail;
        this.password = password;

        this.education = new ArrayList<Education>();
        this.posts = new ArrayList<Post>();
        this.media = new ArrayList<UserMedia>();
    }

    //ADDITIONAL DATA FIELDS
    private ArrayList<Education> education;
    private Work work;
    private String phoneNumber;

    //MEDIA FIELDS
    private ArrayList<Post> posts;
    private ArrayList<UserMedia> media;



    //GETTERS AND SETTERS

    public String getName() {
        return name;
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

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(String graduationDate) {
        this.graduationDate = graduationDate;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Image getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Image profileImage) {
        this.profileImage = profileImage;
    }

    public ArrayList<Education> getEducation() {
        return education;
    }

    public void setEducation(ArrayList<Education> education) {
        this.education = education;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public void addPost(Post post) {
        int id = this.posts.size();
        post.setID(id);
        this.posts.add(post);
    }

    public void removePost(Post post) {
        int off = 0;
        this.posts.remove(post.getID());
        for(Post item : this.posts) {
            item.setID(off);
            off++;
        }
    }

    public ArrayList<UserMedia> getMedia() {
        return media;
    }

    public void setMedia(ArrayList<UserMedia> media) {
        this.media = media;
    }
}
