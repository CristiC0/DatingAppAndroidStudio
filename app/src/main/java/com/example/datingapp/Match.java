package com.example.datingapp;

public class Match {
    private int id;
    private String name;
    private String image;
    private String gender;

    public Match(int id, String name, String image,String gender) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.gender=gender;
    }

    public Match(String name, String image,String gender) {
        this.name = name;
        this.image = image;
        this.gender=gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
