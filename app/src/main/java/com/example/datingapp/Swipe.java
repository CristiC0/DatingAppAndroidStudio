package com.example.datingapp;

public class Swipe {
    private int  id;
    private int from;
    private int to;

    public Swipe( int from, int to) {
        this.from = from;
        this.to = to;
    }

    public Swipe(int id, int from, int to) {
        this.id = id;
        this.from = from;
        this.to = to;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "Swipe{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                '}';
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }
}
