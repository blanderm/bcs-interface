package com.hopologybrewing.bcs.capture.controller;

import com.hopologybrewing.bcs.capture.model.Process;
import com.hopologybrewing.bcs.capture.service.BcsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ddcbryanl on 12/6/16.
 */
@RestController
public class ProcessController extends BcsService {
    private static final Logger log = LoggerFactory.getLogger(ProcessController.class);

    @RequestMapping("/process/{pid}")
    public HttpEntity<Process> getProcess(@PathVariable String pid) {
        return null;
    }

    @RequestMapping("/process/{pid}/states/{sid}")
    public HttpEntity<Process> getState(@PathVariable String pid, @PathVariable String sid) {
        return null;
    }

    @RequestMapping("/process/")
    public HttpEntity<Process> getCurrentData(@PathVariable String pid, @PathVariable String sid) {
        return null;
    }
}
