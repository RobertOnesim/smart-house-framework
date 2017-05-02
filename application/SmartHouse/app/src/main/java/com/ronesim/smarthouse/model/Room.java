package com.ronesim.smarthouse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ronesim on 21.04.2017.
 */

public class Room {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("numberOfDevices")
    @Expose
    private int numberOfDevices;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("date_update")
    @Expose
    private String dateUpdate;
    @SerializedName("lights")
    @Expose
    private List<Light> lights = null;
    private int imageId;


    public Room(int id, String name, String info, String dateUpdate, int numberOfDevices, int imageId) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.numberOfDevices = numberOfDevices;
        this.dateUpdate = dateUpdate;
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

    public String getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public int getNumberOfDevices() {
        return numberOfDevices;
    }

    public void setNumberOfDevices(int numberOfDevices) {
        this.numberOfDevices = numberOfDevices;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", dateUpdate='" + dateUpdate + '\'' +
                ", imageId=" + imageId +
                ", numberOfDevices=" + numberOfDevices +
                '}';
    }
}
