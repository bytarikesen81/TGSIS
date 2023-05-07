package com.teapps.tgsis;

public class Work {
    private String country;
    private String city;
    private String name;
    private String position;

    public Work(String country, String city, String name, String position) {
        this.country = country;
        this.city = city;
        this.name = name;
        this.position = position;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
