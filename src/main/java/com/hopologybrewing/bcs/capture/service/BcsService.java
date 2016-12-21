package com.hopologybrewing.bcs.capture.service;

import com.hopologybrewing.bcs.capture.BasicAuthRestTemplate;
import com.hopologybrewing.bcs.capture.controller.BcsConstants;
import com.hopologybrewing.bcs.capture.model.Output;
import com.hopologybrewing.bcs.capture.model.State;
import com.hopologybrewing.bcs.capture.model.Process;
import com.hopologybrewing.bcs.capture.model.TemperatureProbe;
import com.hopologybrewing.bcs.capture.model.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public abstract class BcsService {
    private static final Logger log = LoggerFactory.getLogger(BcsService.class);
    private static Map<Type, String> urlMap = null;
    private static Map<Type, Class> classMap = null;

    static {
        Map<Type, String> url = new HashMap<>();
        url.put(Type.TEMP, BcsConstants.API_ROOT + "temp/%s");
        url.put(Type.PROCESS, BcsConstants.API_ROOT + "process/%s");
        url.put(Type.PROCESSES, BcsConstants.API_ROOT + "process");
        url.put(Type.STATE, BcsConstants.API_ROOT + "process/%s/state/%s");
        url.put(Type.TIMER, BcsConstants.API_ROOT + "process/%s/timer/%s");
        url.put(Type.OUTPUT, BcsConstants.API_ROOT + "output/%s");
        url.put(Type.OUTPUTS, BcsConstants.API_ROOT + "output");
        urlMap = Collections.unmodifiableMap(url);

        Map<Type, Class> clz = new HashMap<>();
        clz.put(Type.TEMP, TemperatureProbe.class);
        clz.put(Type.PROCESS, Process.class);
        clz.put(Type.PROCESSES, List.class);
        clz.put(Type.STATE, State.class);
        clz.put(Type.TIMER, Timer.class);
        clz.put(Type.OUTPUT, Output.class);
        clz.put(Type.OUTPUTS, ArrayList.class);
        classMap = Collections.unmodifiableMap(clz);
    }

    public Object getData(Type type, String... ids) {
        Object obj = null;
        ResponseEntity response = null;

        try {
            RestTemplate template = new BasicAuthRestTemplate();
            String bcsIp = System.getProperty(BcsConstants.BCS_IP);
            response = template.getForEntity("http://" + bcsIp + String.format(urlMap.get(type), ids), classMap.get(type));
            obj = response.getBody();
        } catch (Throwable t) {
            log.error("Error getting data - ", t);
        }

        return obj;
    }

    public enum Type {TEMP, PROCESS, PROCESSES, STATE, TIMER, OUTPUT, OUTPUTS}
}
