package com.hopologybrewing.bcs.capture.service;

import com.hopologybrewing.bcs.capture.BasicAuthRestTemplate;
import com.hopologybrewing.bcs.capture.BearerAuthRestTemplate;
import com.hopologybrewing.bcs.capture.controller.BcsConstants;
import com.hopologybrewing.bcs.capture.model.*;
import com.hopologybrewing.bcs.capture.model.Process;
import com.hopologybrewing.bcs.capture.model.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class EcobeeService {
    private static final Logger log = LoggerFactory.getLogger(EcobeeService.class);

    public Object getThermostats() {
        Object obj = null;
        ResponseEntity response = null;

        try {
            RestTemplate template = new BearerAuthRestTemplate();
            response = template.getForEntity("https://api.ecobee.com/1/thermostat?format=json", String.class);
            obj = response.getBody();
            log.info("" + obj);
        } catch (Throwable t) {
            log.error("Error getting data - ", t);
        }

        return obj;
    }

    public enum Type {TEMP, PROCESS, PROCESSES, STATE, TIMER, OUTPUT, OUTPUTS}
}
