package model;

import java.io.Serializable;

public class Room implements Serializable {

    private int roomId;



    private String roomNum;
    private int roomCap;
    private String roomWifi;
    private double roomPrice;


    public Room() {
    }


    public Room(int roomId ,String roomNum, int roomCap, String roomWifi, double roomPrice) {
        this.roomId = roomId;
        this.roomNum = roomNum;
        this.roomCap = roomCap;
        this.roomWifi = roomWifi;
        this.roomPrice = roomPrice;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public int getRoomCap() {
        return roomCap;
    }

    public void setRoomCap(int roomCap) {
        this.roomCap = roomCap;
    }

    public String isRoomWifi() {
        return roomWifi;
    }

    public void setRoomWifi(String roomWifi) {
        this.roomWifi = roomWifi;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
