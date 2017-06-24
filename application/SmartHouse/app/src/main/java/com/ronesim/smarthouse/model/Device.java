package com.ronesim.smarthouse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ronesim on 24.06.2017.
 */

public class Device {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("room")
    @Expose
    private int room;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mac_address")
    @Expose
    private String macAddress;
    @SerializedName("brad")
    @Expose
    private String brad;
    @SerializedName("is_on")
    @Expose
    private boolean isOn;

    public Device(int id, int room, String name, String macAddress, String brad, boolean isOn) {
        this.id = id;
        this.room = room;
        this.name = name;
        this.macAddress = macAddress;
        this.brad = brad;
        this.isOn = isOn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getBrad() {
        return brad;
    }

    public void setBrad(String brad) {
        this.brad = brad;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }
}
