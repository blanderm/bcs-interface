package com.hopologybrewing.bcs.capture.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hopologybrewing.bcs.capture.model.Output;
import com.hopologybrewing.bcs.capture.model.OutputRecording;
import com.hopologybrewing.bcs.capture.model.TemperatureProbeRecording;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ddcbryanl on 12/14/16.
 */
public class OutputService extends BcsService {
    private static final Logger log = LoggerFactory.getLogger(OutputService.class);
    private String fileLocation;

    public Output getOutput(String outputId) {
        return (Output) super.getData(BcsService.Type.OUTPUT, outputId);
    }

    public List<Output> getEnabledOutputs() {
        List<Output> enabledOutputs = new ArrayList<Output>();
        List outputs = (List) super.getData(BcsService.Type.OUTPUTS);

        for (int i = 0; i < outputs.size(); i++) {
            if (outputs.get(i) != null) {
                enabledOutputs.add(getOutput(String.valueOf(i)));
            }
        }

        return enabledOutputs;
    }

    public Map<String, List<List>> getHistoricalOutputData() {
        String line;
        List data = null;
        BufferedReader reader = null;
        List<List> recordings = null;
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<List>> outputsMap = new HashMap<>();

        try {
            OutputRecording outputRecording = null;
            reader = new BufferedReader(new FileReader(fileLocation));

            try {
                while ((line = reader.readLine()) != null) {
                    outputRecording = mapper.readValue(line, OutputRecording.class);
                    recordings = outputsMap.get(outputRecording.getOutput().getName());

                    if (recordings == null) {
                        recordings = new ArrayList<>();
                    }

                    data = new ArrayList<>();
                    data.add(outputRecording.getTimestamp());
                    data.add((outputRecording.getOutput().isOn() ? 1 : 0));

                    recordings.add(data);
                    outputsMap.put(outputRecording.getOutput().getName(), recordings);
                }
            } catch (IOException e) {
                log.error("Error reading file " + fileLocation + " - ", e);
            }
        } catch (FileNotFoundException e) {
            log.error("File not found for " + fileLocation + " - ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("Failed to close reader for " + fileLocation + " - ", e);
                }
            }
        }



        return outputsMap;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
}


