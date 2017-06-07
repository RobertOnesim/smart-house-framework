package com.ronesim.smarthouse.model.devices;

/**
 * Created by ronesim on 06.06.2017.
 */

public interface DeviceVisitor {
    void visit(Light light);

    void visit(Plug plug);

    void visit(Thermostat thermostat);

    void visit(Lock lock);

    void visit(Webcam webcam);
}
