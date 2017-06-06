package com.ronesim.smarthouse.model.devices;

/**
 * Created by ronesim on 06.06.2017.
 */

public interface DeviceVisitor {
    int visit(Light light);

    int visit(Plug plug);

    int visit(Thermostat thermostat);

    int visit(Lock lock);

    int visit(Webcam webcam);
}
