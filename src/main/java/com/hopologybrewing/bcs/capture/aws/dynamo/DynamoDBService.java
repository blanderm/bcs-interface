package com.hopologybrewing.bcs.capture.aws.dynamo;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hopologybrewing.bcs.capture.model.Recording;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ddcbryanl on 1/18/17.
 */
public class DynamoDBService {
    private static final Logger log = LoggerFactory.getLogger(DynamoDBService.class);

    // figure out how to query for data in a date range (timestamp)

    // create brews for a beer that have a date range and then retrieve the data for that brew
    // super lightweight, allows you to record data over time and associate data points with a brew through simple date range mapping (between query)

    // create an interface that allows brew creation/initiation and edit

    public List<Recording> queryRecordingForTimestampRange(String tableName, String id, long timestampLowerBound, long timestampUpperBound) {

        AmazonDynamoDBClient client = new AmazonDynamoDBClient()
                .withRegion(Regions.US_EAST_1);

        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable(tableName);

        // get values in specified range
        RangeKeyCondition rangedKeyCondition = new RangeKeyCondition("timestamp");
        rangedKeyCondition.between(timestampLowerBound, timestampUpperBound);

        ItemCollection<QueryOutcome> items = table.query("id", id, rangedKeyCondition);

        List<Recording> results = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (Item item : items) {
            try {
                results.add((Recording) mapper.readValue(item.toJSON(), DynamoConstants.tableRecordingMap.get(tableName)));
            } catch (IOException e) {
                log.error("Failed parsing json ", e);
            }

        }

        return results;
    }

    public Item writeRecording(String tableName, Recording obj) {
        AmazonDynamoDBClient client = new AmazonDynamoDBClient()
                .withRegion(Regions.US_EAST_1);

        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable(tableName);
        PutItemOutcome outcome = table.putItem(getItem(obj));

        return outcome.getItem();
    }

    private Item getItem(Recording recording) {
        ObjectMapper mapper = new ObjectMapper();
        Item item = null;

        try {
            item = new Item()
                    .withKeyComponent("id", recording.getId())
                    .withKeyComponent("timestamp", recording.getTimestamp().getTime())
                    .withJSON("data", mapper.writeValueAsString(recording.getData()));
        } catch (JsonProcessingException e) {
            log.error("Failed creating json for enabled outputs - ", e);
        }

        return item;
    }
}
