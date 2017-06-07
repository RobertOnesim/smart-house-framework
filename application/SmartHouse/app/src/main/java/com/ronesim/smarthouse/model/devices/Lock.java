package com.ronesim.smarthouse.model.devices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ronesim.smarthouse.R;

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
    public void accept(DeviceVisitor deviceVisitor) {
        deviceVisitor.visit(this);
    }

    @Override
    public int getImageLogo() {
        return R.drawable.logo_lock;
    }

    @Override
    public String getType() {
        return "lock";
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
