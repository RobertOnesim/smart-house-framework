package com.ronesim.smarthouse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ronesim on 03.05.2017.
 */
// TODO ronesim add abstract class
public class Light {

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
    @SerializedName("is_on")
    @Expose
    private boolean isOn;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("intensity")
    @Expose
    private double intensity;

    public Light(String name) {
        this.name = name;
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

    public boolean isIsOn() {
        return isOn;
    }

    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }
}
