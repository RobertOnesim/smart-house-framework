package com.ronesim.smarthouse.model.devices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ronesim on 03.05.2017.
 */
public class Light extends Device {

    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("intensity")
    @Expose
    private double intensity;

    @Override
    public int accept(DeviceVisitor deviceVisitor) {
        return deviceVisitor.visit(this);
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

    @Override
    public String toString() {
        return "Light{" +
                "color='" + color + '\'' +
                ", intensity=" + intensity +
                '}';
    }
}
