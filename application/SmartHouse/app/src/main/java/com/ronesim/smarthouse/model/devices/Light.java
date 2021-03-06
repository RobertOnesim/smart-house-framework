package com.ronesim.smarthouse.model.devices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ronesim.smarthouse.R;

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

    public Light(com.ronesim.smarthouse.model.Device device) {
        setId(device.getId());
        setName(device.getName());
        setRoom(device.getRoom());
        setBrad(device.getBrad());
        setMacAddress(device.getMacAddress());
        setOn(device.isOn());
        this.color = "150 150 150";
        this.intensity = 1.0;
    }

    @Override
    public void accept(DeviceVisitor deviceVisitor) {
        deviceVisitor.visit(this);
    }

    @Override
    public int getImageLogo() {
        return R.drawable.logo_light;
    }

    @Override
    public String getType() {
        return "light";
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
