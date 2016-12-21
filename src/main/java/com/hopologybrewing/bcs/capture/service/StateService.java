package com.hopologybrewing.bcs.capture.service;

import com.hopologybrewing.bcs.capture.model.State;
import com.hopologybrewing.bcs.capture.model.Process;
import com.hopologybrewing.bcs.capture.model.Timer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ddcbryanl on 12/20/16.
 */
public class StateService extends BcsService {
    public State getState(String processId, String stateId) {
        return (State) getData(Type.STATE, processId, stateId);
    }

    public State getCurrentState(String processId) {
        // get process to find active state
        Process p = (Process) getData(Type.PROCESS, processId);

        State state = null;
        if (p.isRunning() && p.getCurrentState() != null) {
            Integer stateId = p.getCurrentState().getState();
            state = (State) getData(Type.STATE, processId, String.valueOf(stateId));

            if (state.getTimers() != null) {
                Timer timer;
                List<Timer> newTimerList = new ArrayList<Timer>();
                for (int i = 0; i < state.getTimers().size(); i++) {
                    timer = state.getTimers().get(i);

                    if (timer.isUsed()) {
                        timer = (Timer) getData(Type.TIMER, processId, String.valueOf(i));
                        newTimerList.add(i, timer);
                    }
                }

                state.setTimers(newTimerList);
            }
        }

        return state;
    }
}
