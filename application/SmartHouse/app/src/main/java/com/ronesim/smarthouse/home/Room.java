package com.ronesim.smarthouse.home;

/**
 * Created by ronesim on 21.04.2017.
 */

public class Room {
    private int roomId;
    private String name;
    private String info;
    private String date;
    private int imageId;
    private int number_of_devices;

    public Room(int roomId, String name, String info, String date, int number_of_devices, int imageId) {
        this.roomId = roomId;
        this.name = name;
        this.info = info;
        this.number_of_devices = number_of_devices;
        this.date = date;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumber_of_devices() {
        return number_of_devices;
    }

    public void setNumber_of_devices(int number_of_devices) {
        this.number_of_devices = number_of_devices;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", date='" + date + '\'' +
                ", imageId=" + imageId +
                ", number_of_devices=" + number_of_devices +
                '}';
    }
}
