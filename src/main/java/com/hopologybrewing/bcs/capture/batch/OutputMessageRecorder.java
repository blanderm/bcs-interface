package com.hopologybrewing.bcs.capture.batch;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hopologybrewing.bcs.capture.model.Output;
import com.hopologybrewing.bcs.capture.model.OutputRecording;
import com.hopologybrewing.bcs.capture.model.TemperatureProbe;
import com.hopologybrewing.bcs.capture.model.TemperatureProbeRecording;
import com.hopologybrewing.bcs.capture.service.OutputService;
import com.hopologybrewing.bcs.capture.service.TemperatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class OutputMessageRecorder {
    private static final Logger log = LoggerFactory.getLogger(OutputMessageRecorder.class);
    private static final Logger historyLogger = LoggerFactory.getLogger("bcs-outputs-history");
    private OutputService outputService;

    public String getNextOutputReading() {
        StringBuffer probeValue = null;

        try {
            Date date = new Date();
            ObjectMapper mapper = new ObjectMapper();
            OutputRecording recording = null;
            List<Output> outputs = outputService.getEnabledOutputs();

            if (outputs != null && !outputs.isEmpty()) {
                probeValue = new StringBuffer();

                for (Output output : outputs) {
                    recording = new OutputRecording(output, date);
                    probeValue.append(mapper.writeValueAsString(recording)).append("\n");
                }
            }
        } catch (JsonProcessingException e) {
            log.error("Failed creating json for enabled probes - ", e);
        }

        return (probeValue == null ? null : probeValue.toString());
    }

    public void recordMessage(String message) {
        historyLogger.info(message);
    }

    @Autowired

    public void setOutputService(OutputService outputService) {
        this.outputService = outputService;
    }
}
