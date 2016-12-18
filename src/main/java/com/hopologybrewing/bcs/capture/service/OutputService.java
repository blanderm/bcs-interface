package com.hopologybrewing.bcs.capture.service;

import com.hopologybrewing.bcs.capture.model.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ddcbryanl on 12/14/16.
 */
public class OutputService extends BcsService {
    private static final Logger log = LoggerFactory.getLogger(OutputService.class);

    public Output getOutput(String outputId) {
        return (Output) super.getData(BcsService.Type.OUTPUT, outputId);
    }

    public List<Output> getEnabledOutputs() {
        List<Output> enabledOutputs = new ArrayList<Output>();
        List outputs = (List) super.getData(BcsService.Type.OUTPUTS);

        for (int i = 0; i < outputs.size(); i++) {
            if (outputs.get(i) != null) {
                enabledOutputs.add(getOutput(String.valueOf(i)));
            }
        }

        return enabledOutputs;
    }
}


