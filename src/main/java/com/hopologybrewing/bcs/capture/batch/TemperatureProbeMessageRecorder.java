package com.hopologybrewing.bcs.capture.batch;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hopologybrewing.bcs.capture.model.TemperatureProbe;
import com.hopologybrewing.bcs.capture.model.TemperatureProbeRecording;
import com.hopologybrewing.bcs.capture.service.TemperatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TemperatureProbeMessageRecorder {
    private static final Logger log = LoggerFactory.getLogger("bcs-temps-history");
    private AtomicInteger lastProbe = new AtomicInteger(1);
    private TemperatureService tempService;

    public String getNextTemperatureReading() {
        StringBuffer probeValue = null;

        try {
            Date date = new Date();
            ObjectMapper mapper = new ObjectMapper();
            TemperatureProbeRecording recording = null;
            List<TemperatureProbe> probes = tempService.getEnabledProbes();

            if (probes != null && !probes.isEmpty()) {
                probeValue = new StringBuffer();

                for (TemperatureProbe probe : probes) {
                    recording = new TemperatureProbeRecording(probe, date);
                    probeValue.append(mapper.writeValueAsString(recording)).append("\n");
                }
            }
        } catch (JsonProcessingException e) {
            log.error("Failed creating json for enabled probes - ", e);
        }

        return (probeValue == null ? null : probeValue.toString());
    }

    public void recordMessage(String message) {
        log.info(message);
    }

    @Autowired
    public void setTempService(TemperatureService tempService) {
        this.tempService = tempService;
    }
}
