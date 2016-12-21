package com.hopologybrewing.bcs.capture.service;

import java.util.List;
import com.hopologybrewing.bcs.capture.model.Process;

/**
 * Created by ddcbryanl on 12/20/16.
 */
public class ProcessService extends BcsService {
    public Process getProcess(String processId) {
        return (Process) getData(Type.PROCESS, processId);
    }

    public List getActiveProcesses() {
        return (List) getData(Type.PROCESSES);
    }

    public List<Process> getProcesses() {
        return (List<Process>) getData(Type.PROCESSES);
    }
}
