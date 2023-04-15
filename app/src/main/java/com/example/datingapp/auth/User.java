package com.example.datingapp.auth;

public class User {
    private int id;
    private String username;
    private String password;
    private String gender;


    public User(int id,String username,String password,String gender){
        this.id=id;
        this.gender=gender;
        this.password=password;
        this.username=username;
    }

    public User(String username,String password,String gender){
        this.gender=gender;
        this.password=password;
        this.username=username;
    }

    public User(String username,String password){
        this.password=password;
        this.username=username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
