package com.ronesim.smarthouse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ronesim.smarthouse.model.devices.Device;
import com.ronesim.smarthouse.model.devices.Light;
import com.ronesim.smarthouse.model.devices.Lock;
import com.ronesim.smarthouse.model.devices.Plug;
import com.ronesim.smarthouse.model.devices.Thermostat;
import com.ronesim.smarthouse.model.devices.Webcam;

import java.util.ArrayList;
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
    @SerializedName("number_of_devices")
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
    @SerializedName("locks")
    @Expose
    private List<Lock> locks = null;
    @SerializedName("plugs")
    @Expose
    private List<Plug> plugs = null;
    @SerializedName("thermostats")
    @Expose
    private List<Thermostat> thermostats = null;
    @SerializedName("webcams")
    @Expose
    private List<Webcam> webcams = null;

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

    public List<Light> getLights() {
        return lights;
    }

    public void setLights(List<Light> lights) {
        this.lights = lights;
    }

    public List<Lock> getLocks() {
        return locks;
    }

    public void setLocks(List<Lock> locks) {
        this.locks = locks;
    }

    public List<Plug> getPlugs() {
        return plugs;
    }

    public void setPlugs(List<Plug> plugs) {
        this.plugs = plugs;
    }

    public List<Thermostat> getThermostats() {
        return thermostats;
    }

    public void setThermostats(List<Thermostat> thermostats) {
        this.thermostats = thermostats;
    }

    public List<Webcam> getWebcams() {
        return webcams;
    }

    public void setWebcams(List<Webcam> webcams) {
        this.webcams = webcams;
    }

    /**
     * Put (lights, plugs, webcams, thermostats, locks) all together
     *
     * @return a list of all devices
     */
    public List<Device> getDevices() {
        List<Device> devices = new ArrayList<>();
        devices.addAll(lights);
        devices.addAll(plugs);
        devices.addAll(thermostats);
        devices.addAll(webcams);
        devices.addAll(locks);
        return devices;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numberOfDevices=" + numberOfDevices +
                ", info='" + info + '\'' +
                ", dateUpdate='" + dateUpdate + '\'' +
                ", lights=" + lights +
                ", locks=" + locks +
                ", plugs=" + plugs +
                ", thermostats=" + thermostats +
                ", webcams=" + webcams +
                ", imageId=" + imageId +
                '}';
    }
}
