package com.example.networkdemo;

import java.io.Serializable;

// a class for a move on the board.
// ex: Move(0,1, 'x') represents board[0][1] with token 'x'
public class Move implements Serializable {
    private int x;
    private int y;
    private static char token;
//    private String room_id;

    Move(int x,int y, char token) {
        this.x = x;
        this.y = y;
        this.token = token;
//        this.room_id = room_id;
    }
    Move(int x, int y){
        this.x = x;
        this.y = y;
    }

    int getX() { return x; }

    int getY() { return y; }

    static char getToken() { return token; }

//    String getRoom_id() { return room_id; }

    void setX(int x) { this.x = x; }

    void setY(int y) { this.y = y; }

    static void setToken(char t) { token = t; }

//    void setRoom_id(String room_id) { this.room_id = room_id; }
}
