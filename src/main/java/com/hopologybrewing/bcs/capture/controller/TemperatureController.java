package com.hopologybrewing.bcs.capture.controller;

import com.hopologybrewing.bcs.capture.model.TemperatureProbe;
import com.hopologybrewing.bcs.capture.service.TemperatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
public class TemperatureController {
    private static final Logger log = LoggerFactory.getLogger(TemperatureController.class);
    private TemperatureService tempService;

    @RequestMapping("/temp")
    public HttpEntity<List<TemperatureProbe>> getTemps() {
        return new HttpEntity<List<TemperatureProbe>>(tempService.getEnabledProbes());
    }

    @RequestMapping("/temp/{tid}")
    public HttpEntity<String> getTemp(@PathVariable String tid) {
        TemperatureProbe probe = tempService.getProbe(tid);

        StringBuffer buffer = new StringBuffer();
        buffer.append("[{ \"name\": \"").append(probe.getName()).append("\",\"setpoint\": ")
                .append(probe.getSetpoint()/10).append(",\"data\": [").append(probe.getTemp()/10).append("]}]");
        return new HttpEntity<String>(buffer.toString());
    }

    @RequestMapping("/temp/history")
    public HttpEntity<String> getHistoricalTemps() {
        return new HttpEntity<String>(tempService.getHistoricalProbeData());
    }

    @Autowired
    public void setTempService(TemperatureService tempService) {
        this.tempService = tempService;
    }
}
