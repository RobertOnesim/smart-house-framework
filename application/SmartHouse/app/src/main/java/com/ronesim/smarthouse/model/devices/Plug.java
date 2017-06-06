package com.ronesim.smarthouse.model.devices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ronesim on 06.06.2017.
 */

public class Plug extends Device {
    @SerializedName("away_mode")
    @Expose
    private boolean awayMode;
    @SerializedName("schedule")
    @Expose
    private String schedule;

    @Override
    public int accept(DeviceVisitor deviceVisitor) {
        return deviceVisitor.visit(this);
    }

    public boolean isAwayMode() {
        return awayMode;
    }

    public void setAwayMode(boolean awayMode) {
        this.awayMode = awayMode;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "Plug{" +
                "awayMode=" + awayMode +
                ", schedule='" + schedule + '\'' +
                '}';
    }
}
