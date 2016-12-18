package com.hopologybrewing.bcs.capture.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hopologybrewing.bcs.capture.controller.BcsConstants;
import com.hopologybrewing.bcs.capture.model.TemperatureProbe;
import com.hopologybrewing.bcs.capture.model.TemperatureProbeRecording;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TemperatureService extends BcsService {
    private static final Logger log = LoggerFactory.getLogger(TemperatureService.class);
    private String fileLocation;

    public String getHistoricalProbeData() {
        String line;
        List data = null;
        BufferedReader reader = null;
        List<List> recordings = null;
        ObjectMapper mapper = new ObjectMapper();
        StringBuffer buffer = new StringBuffer();
        Map<String, List<List>> probesMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        try {
            TemperatureProbeRecording probeRecording = null;
            reader = new BufferedReader(new FileReader(fileLocation));

            try {
                while ((line = reader.readLine()) != null) {
                    probeRecording = mapper.readValue(line, TemperatureProbeRecording.class);
                    recordings = probesMap.get(probeRecording.getProbe().getName());

                    if (recordings == null) {
                        recordings = new ArrayList<>();
                    }

                    data = new ArrayList<>();
//                    data.add(sdf.format(probeRecording.getTimestamp()));
                    data.add(probeRecording.getTimestamp());
                    data.add(new Double(probeRecording.getProbe().getTemp())/10);

                    recordings.add(data);
                    probesMap.put(probeRecording.getProbe().getName(), recordings);
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

        try {
            buffer.append("[");
            String name;
            for(Iterator<String> it = probesMap.keySet().iterator(); it.hasNext();) {
                name = it.next();
                buffer.append("{\"name\": \"").append(name).append("\", \"data\":")
                        .append(mapper.writeValueAsString(probesMap.get(name))).append("}");

                if (it.hasNext()){
                    buffer.append(",");
                }
            }

            buffer.append("]");
        } catch (JsonProcessingException e) {
            log.error("Failed to convert the result to json - ", e);
        }

        return buffer.toString();
    }

    public List<TemperatureProbe> getEnabledProbes() {
        TemperatureProbe probe = null;
        ResponseEntity<TemperatureProbe> response = null;
        List<TemperatureProbe> probes = new ArrayList<TemperatureProbe>();

        try {
            for (int i = 0; i < BcsConstants.TEMP_PROBE_COUNT; i++) {
                probe = getProbe(String.valueOf(i));

                if (probe != null && probe.isEnabled()) {
                    probes.add(probe);
                }
            }
        } catch (Throwable t) {
            log.error("Error getting temps - ", t);
        }

        return probes;
    }

    public TemperatureProbe getProbe(String probeId) {
        return (TemperatureProbe) super.getData(BcsService.Type.TEMP, probeId);
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
}
