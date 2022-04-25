package com.example.networkdemo;

import java.io.Serializable;

public class Move implements Serializable {
    private int x;
    private int y;
    private char token;
    private String room_id;

    Move(int x,int y, char token, String room_id) {
        this.x = x;
        this.y = y;
        this.token = token;
        this.room_id = room_id;
    }

    int getX() { return x; }

    int getY() { return y; }

    char getToken() { return token; }

    String getRoom_id() { return room_id; }

    void setX(int x) { this.x = x; }

    void setY(int y) { this.y = y; }

    void setToken(char token) { this.token = token; }

    void setRoom_id(String room_id) { this.room_id = room_id; }
}
