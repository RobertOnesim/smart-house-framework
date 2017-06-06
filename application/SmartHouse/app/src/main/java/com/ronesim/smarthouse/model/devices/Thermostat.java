package com.ronesim.smarthouse.model.devices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    @Override
    public int accept(DeviceVisitor deviceVisitor) {
        return deviceVisitor.visit(this);
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
