package com.hopologybrewing.bcs.capture.controller;

import com.hopologybrewing.bcs.capture.model.Output;
import com.hopologybrewing.bcs.capture.model.TemperatureProbe;
import com.hopologybrewing.bcs.capture.service.OutputService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OutputController {
    private static final Logger log = LoggerFactory.getLogger(OutputController.class);
    private OutputService outputService;

    @RequestMapping("/output/{oid}")
    public HttpEntity<Output> getOutput(@PathVariable String oid) {
        return new HttpEntity<Output>(outputService.getOutput(oid));
    }

    @RequestMapping("/output")
    public HttpEntity<List<Output>> getEnabledOutputs() {
        return new HttpEntity<List<Output>>(outputService.getEnabledOutputs());
    }

    @Autowired
    public void setOutputService(OutputService outputService) {
        this.outputService = outputService;
    }
}
