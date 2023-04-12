package com.example.datingapp;

public class CurrentUser {
    private static CurrentUser instance=null;
    private User user;
    public CurrentUser(){
    }

    public static synchronized CurrentUser getInstance()
    {
        if (instance == null)
            instance = new CurrentUser();
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
