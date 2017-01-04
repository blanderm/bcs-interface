package com.hopologybrewing.bcs.capture.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class OutputRecording {
    private Output output;
    protected Date timestamp;

    public OutputRecording() {
    }

    public OutputRecording(Output o, Date timestamp) {
        this.output = o;
        this.timestamp = timestamp;
    }

    public Output getOutput() {
        return (Output) output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
