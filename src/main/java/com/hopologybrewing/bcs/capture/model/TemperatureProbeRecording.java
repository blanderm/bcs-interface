package com.hopologybrewing.bcs.capture.model;

import java.util.Date;

/**
 * Created by ddcbryanl on 12/13/16.
 */
public class TemperatureProbeRecording {
    private TemperatureProbe probe;
    private Date timestamp;

    public TemperatureProbeRecording() {
    }

    public TemperatureProbeRecording(TemperatureProbe probe, Date timestamp) {
        this.probe = probe;
        this.timestamp = timestamp;
    }

    public TemperatureProbe getProbe() {
        return probe;
    }

    public void setProbe(TemperatureProbe probe) {
        this.probe = probe;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
