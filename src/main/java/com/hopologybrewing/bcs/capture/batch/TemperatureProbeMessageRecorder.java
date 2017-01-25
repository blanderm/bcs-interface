package com.hopologybrewing.bcs.capture.batch;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hopologybrewing.bcs.capture.aws.dynamo.DynamoConstants;
import com.hopologybrewing.bcs.capture.aws.dynamo.DynamoDBService;
import com.hopologybrewing.bcs.capture.model.TemperatureProbe;
import com.hopologybrewing.bcs.capture.model.TemperatureProbeRecording;
import com.hopologybrewing.bcs.capture.service.TemperatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TemperatureProbeMessageRecorder {
    //{"probe":{"name":"Left Fermentor","temp":394.0,"setpoint":380.0,"resistance":26450.0,"enabled":true,"coefficients":[0.0011371549,2.325949E-4,9.5400029999E-8]},"timestamp":1484764722877}
    //{"probe":{"name":"Right Fermentor","temp":389.0,"setpoint":380.0,"resistance":26792.0,"enabled":true,"coefficients":[0.0011371549,2.325949E-4,9.5400029999E-8]},"timestamp":1484764722877}
    private static final Logger log = LoggerFactory.getLogger(TemperatureProbeMessageRecorder.class);
    private static final Logger historyLogger = LoggerFactory.getLogger("bcs-temps-history");
    private TemperatureService tempService;
    private DynamoDBService dynamoDbService;

    public List<TemperatureProbeRecording> getNextTemperatureReading() {
        Date date = new Date();
        List<TemperatureProbe> probes = tempService.getEnabledProbes();
        List<TemperatureProbeRecording> recordings = new ArrayList<>();

        if (probes != null && !probes.isEmpty()) {

            for (TemperatureProbe probe : probes) {
                recordings.add(new TemperatureProbeRecording(probe, date));
            }
        }

        return recordings;
    }

    public void recordMessage(List<TemperatureProbeRecording> message) {
        if (message != null) {
            ObjectMapper mapper = new ObjectMapper();
            
            for (TemperatureProbeRecording recording : message) {
                try {
                    historyLogger.info(mapper.writeValueAsString(message) + "\n");
                } catch (JsonProcessingException e) {
                    log.error("Failed creating json ", e);
                }

                // put message in DynamoDB
                dynamoDbService.writeRecording(DynamoConstants.TEMPERATURE_READINGS_TABLE, recording);
            }
        }
    }

    @Autowired
    public void setTempService(TemperatureService tempService) {
        this.tempService = tempService;
    }

    @Autowired
    public void setDynamoDbService(DynamoDBService dynamoDbService) {
        this.dynamoDbService = dynamoDbService;
    }
}
