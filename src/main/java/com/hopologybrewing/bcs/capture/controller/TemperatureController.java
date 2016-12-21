package com.hopologybrewing.bcs.capture.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hopologybrewing.bcs.capture.model.TemperatureProbe;
import com.hopologybrewing.bcs.capture.service.EcobeeService;
import com.hopologybrewing.bcs.capture.service.TemperatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
public class TemperatureController {
    private static final Logger log = LoggerFactory.getLogger(TemperatureController.class);
    private TemperatureService tempService;
    private EcobeeService ecobeeService;

    @RequestMapping("/temp")
    public HttpEntity<List<TemperatureProbe>> getTemps() {
        return new HttpEntity<List<TemperatureProbe>>(tempService.getEnabledProbes());
    }

    @RequestMapping("/thermostat")
    public HttpEntity<Object> getThermostats() {
        return new HttpEntity<Object>(ecobeeService.getThermostats());
    }

    @RequestMapping("/temp/{tid}")
    public HttpEntity<String> getTemp(@PathVariable String tid) {
        TemperatureProbe probe = tempService.getProbe(tid);

        StringBuffer buffer = new StringBuffer();
        buffer.append("[{ \"name\": \"").append(probe.getName()).append("\",\"setpoint\": ")
                .append(probe.getSetpoint() / 10).append(",\"data\": [").append(probe.getTemp() / 10).append("]}]");
        return new HttpEntity<String>(buffer.toString());
    }

    @RequestMapping("/temp/history")
    public HttpEntity<String> getHistoricalTemps() {
        StringBuffer buffer = new StringBuffer();
        Map<String, List<List>> probesMap = tempService.getHistoricalProbeData();
        ObjectMapper mapper = new ObjectMapper();

        try {
            buffer.append("[");
            String name;
            for (Iterator<String> it = probesMap.keySet().iterator(); it.hasNext(); ) {
                name = it.next();
                buffer.append("{\"name\": \"").append(name).append("\", \"data\":")
                        .append(mapper.writeValueAsString(probesMap.get(name))).append("}");

                if (it.hasNext()) {
                    buffer.append(",");
                }
            }

            buffer.append("]");
        } catch (JsonProcessingException e) {
            log.error("Failed to convert the result to json - ", e);
        }

        return new HttpEntity<String>(buffer.toString());
    }

    @Autowired
    public void setTempService(TemperatureService tempService) {
        this.tempService = tempService;
    }

    @Autowired
    public void setEcobeeService(EcobeeService ecobeeService) {
        this.ecobeeService = ecobeeService;
    }
}
