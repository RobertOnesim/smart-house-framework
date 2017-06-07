package com.ronesim.smarthouse.model.devices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ronesim.smarthouse.R;

/**
 * Created by ronesim on 06.06.2017.
 */

public class Webcam extends Device {
    @SerializedName("night_vision")
    @Expose
    private boolean nightVision;
    @SerializedName("motion_detection")
    @Expose
    private boolean motionDetection;

    @Override
    public void accept(DeviceVisitor deviceVisitor) {
        deviceVisitor.visit(this);
    }

    @Override
    public int getImageLogo() {
        return R.drawable.logo_webcam;
    }

    @Override
    public String getType() {
        return "webcam";
    }

    public boolean isNightVision() {
        return nightVision;
    }

    public void setNightVision(boolean nightVision) {
        this.nightVision = nightVision;
    }

    public boolean isMotionDetection() {
        return motionDetection;
    }

    public void setMotionDetection(boolean motionDetection) {
        this.motionDetection = motionDetection;
    }

    @Override
    public String toString() {
        return "Webcam{" +
                "nightVision=" + nightVision +
                ", motionDetection=" + motionDetection +
                '}';
    }
}
