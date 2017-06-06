package com.ronesim.smarthouse.model.devices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ronesim on 06.06.2017.
 */

public abstract class Device {
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

    public abstract int accept(DeviceVisitor deviceVisitor);

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

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", room=" + room +
                ", name='" + name + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", isOn=" + isOn +
                '}';
    }
}
