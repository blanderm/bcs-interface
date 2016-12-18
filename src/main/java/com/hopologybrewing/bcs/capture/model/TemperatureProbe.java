package com.hopologybrewing.bcs.capture.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by ddcbryanl on 12/5/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemperatureProbe {
    private String name;
    private int temp;
    private int setpoint;
    private double resistance;
    private boolean enabled;
    private List<Double> coefficients;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getSetpoint() {
        return setpoint;
    }

    public void setSetpoint(int setpoint) {
        this.setpoint = setpoint;
    }

    public double getResistance() {
        return resistance;
    }

    public void setResistance(double resistance) {
        this.resistance = resistance;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Double> getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(List<Double> coefficients) {
        this.coefficients = coefficients;
    }
}
