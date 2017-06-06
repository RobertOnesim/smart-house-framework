package com.ronesim.smarthouse.model.devices;

import com.ronesim.smarthouse.R;

/**
 * Created by ronesim on 06.06.2017.
 */

public class DeviceDisplayVisitor implements DeviceVisitor {
    @Override
    public int visit(Light light) {
        return R.drawable.logo_light;
    }

    @Override
    public int visit(Plug plug) {
        return R.drawable.logo_plug;
    }

    @Override
    public int visit(Thermostat thermostat) {
        return R.drawable.logo_thermostat;
    }

    @Override
    public int visit(Lock lock) {
        return R.drawable.logo_lock;
    }

    @Override
    public int visit(Webcam webcam) {
        return R.drawable.logo_webcam;
    }
}
