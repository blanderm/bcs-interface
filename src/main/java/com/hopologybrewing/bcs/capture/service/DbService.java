package com.hopologybrewing.bcs.capture.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hopologybrewing.bcs.capture.aws.dynamo.DynamoConstants;
import com.hopologybrewing.bcs.capture.model.Recording;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ddcbryanl on 1/27/17.
 */
public interface DbService {
    public List<Recording> queryRecording(Class responseObjClass, String id, long timestampLowerBound, long timestampUpperBound, int limit, boolean ascendingOrder);

    public Item writeRecording(Recording obj);
}
