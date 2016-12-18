package com.hopologybrewing.bcs.capture.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.lang.*;
import java.lang.Process;
import java.util.List;

/**
 * Created by ddcbryanl on 12/5/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class State {
    private String name;
    private List<Timer> timers;
    private Ramp ramp;
    private List<Output> outputs;
    private List<java.lang.Process> processSpawn;
    private List<java.lang.Process> processKill;
    private int stateAlarm;
    private boolean emailAlarm;
    private List<Register> registers;

    public State(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Timer> getTimers() {
        return timers;
    }

    public void setTimers(List<Timer> timers) {
        this.timers = timers;
    }

    public Ramp getRamp() {
        return ramp;
    }

    public void setRamp(Ramp ramp) {
        this.ramp = ramp;
    }

    public List<Output> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<Output> outputs) {
        this.outputs = outputs;
    }

    public List<Process> getProcessSpawn() {
        return processSpawn;
    }

    public void setProcessSpawn(List<Process> processSpawn) {
        this.processSpawn = processSpawn;
    }

    public List<Process> getProcessKill() {
        return processKill;
    }

    public void setProcessKill(List<Process> processKill) {
        this.processKill = processKill;
    }

    public int getStateAlarm() {
        return stateAlarm;
    }

    public void setStateAlarm(int stateAlarm) {
        this.stateAlarm = stateAlarm;
    }

    public boolean isEmailAlarm() {
        return emailAlarm;
    }

    public void setEmailAlarm(boolean emailAlarm) {
        this.emailAlarm = emailAlarm;
    }

    public List<Register> getRegisters() {
        return registers;
    }

    public void setRegisters(List<Register> registers) {
        this.registers = registers;
    }
}
