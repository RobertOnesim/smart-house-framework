package com.ronesim.smarthouse.model.devices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ronesim.smarthouse.R;

/**
 * Created by ronesim on 06.06.2017.
 */

public class Thermostat extends Device {
    @SerializedName("temperature")
    @Expose
    private int temperature;
    @SerializedName("humidity")
    @Expose
    private int humidity;

    public Thermostat(com.ronesim.smarthouse.model.Device device) {
        setId(device.getId());
        setName(device.getName());
        setRoom(device.getRoom());
        setBrad(device.getBrad());
        setMacAddress(device.getMacAddress());
        setOn(device.isOn());
        this.temperature = 0;
        this.humidity = 0;
    }

    @Override
    public void accept(DeviceVisitor deviceVisitor) {
        deviceVisitor.visit(this);
    }

    @Override
    public int getImageLogo() {
        return R.drawable.logo_thermostat;
    }

    @Override
    public String getType() {
        return "thermostat";
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "Thermostat{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                '}';
    }
}
