package com.hopologybrewing.bcs.capture.controller;

import com.hopologybrewing.bcs.capture.BasicAuthRestTemplate;
import com.hopologybrewing.bcs.capture.model.Process;
import com.hopologybrewing.bcs.capture.model.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by ddcbryanl on 12/6/16.
 */
@RestController
public class ProcessController {
    private static final Logger log = LoggerFactory.getLogger(ProcessController.class);

    @RequestMapping("/process/{pid}")
    public HttpEntity<Process> getProcess(@PathVariable String pid) {
        ResponseEntity<Process> process = null;
        try {
            RestTemplate template = new BasicAuthRestTemplate();
            process = template.getForEntity("http://73.159.98.34/api/process/" + pid, Process.class);
        } catch (Throwable t) {
            log.error("Error getting process 1 - ", t);
        }

        return process;
    }

    @RequestMapping("/process/{pid}/states/{sid}")
    public HttpEntity<Process> getState(@PathVariable String pid, @PathVariable String sid) {
        ResponseEntity<Process> process = null;
        try {
            RestTemplate template = new BasicAuthRestTemplate();
            process = template.getForEntity("http://73.159.98.34/api/process/" + pid + "/state/" + sid, Process.class);
        } catch (Throwable t) {
            log.error("Error getting process 1 - ", t);
        }

        return process;
    }

    @RequestMapping("/process/")
    public HttpEntity<Process> getCurrentData(@PathVariable String pid, @PathVariable String sid) {
        ResponseEntity<Process> process = null;
        ResponseEntity<State> state = null;
        try {
            RestTemplate template = new BasicAuthRestTemplate();
            process = template.getForEntity("http://73.159.98.34/api/process/" + pid + "/state/" + sid, Process.class);

            if (process.getBody() != null) {
                state = template.getForEntity("http://73.159.98.34/api/process/" + pid + "/state/" + sid, State.class);
            }

        } catch (Throwable t) {
            log.error("Error getting process 1 - ", t);
        }

        return process;
    }
}
