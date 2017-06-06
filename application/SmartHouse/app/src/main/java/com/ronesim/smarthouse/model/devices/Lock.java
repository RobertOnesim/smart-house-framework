package com.ronesim.smarthouse.model.devices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ronesim on 06.06.2017.
 */

public class Lock extends Device {
    @SerializedName("pin_code")
    @Expose
    private int pinCode;
    @SerializedName("voice_activation")
    @Expose
    private boolean voiceActivation;

    @Override
    public int accept(DeviceVisitor deviceVisitor) {
        return deviceVisitor.visit(this);
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public boolean isVoiceActivation() {
        return voiceActivation;
    }

    public void setVoiceActivation(boolean voiceActivation) {
        this.voiceActivation = voiceActivation;
    }

    @Override
    public String toString() {
        return "Lock{" +
                "pinCode=" + pinCode +
                ", voiceActivation=" + voiceActivation +
                '}';
    }
}
