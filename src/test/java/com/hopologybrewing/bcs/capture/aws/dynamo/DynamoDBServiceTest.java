package com.hopologybrewing.bcs.capture.aws.dynamo;

import com.hopologybrewing.bcs.capture.model.Recording;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ddcbryanl on 1/25/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration("classpath:applicationContext.xml")
public class DynamoDBServiceTest {
    private DynamoDBService dynamoDbService;

    @Test
    public void queryRecordingForTimestampRange() throws Exception {
        Date date = new Date();
        date.setTime(1485318714620L);
        long upper = date.getTime();
        long lower = upper - 5000;
        List<Recording> recordings = dynamoDbService.queryRecordingForTimestampRange(DynamoConstants.OUTPUT_READINGS_TABLE, "Pump - Left", lower, upper);
        checkRange(recordings, lower, upper);

        recordings = dynamoDbService.queryRecordingForTimestampRange(DynamoConstants.OUTPUT_READINGS_TABLE, "Pump - Right", lower, upper);
        checkRange(recordings, lower, upper);
    }

    private void checkRange(List<Recording> recordings, long lower, long upper) {
        for (Recording r : recordings) {
            assertThat("Timestamp is in range", r.getTimestamp(), new BaseMatcher<Date>() {
                @Override
                public boolean matches(Object item) {
                    if (item instanceof Date) {
                        Date d = (Date) item;
                        return d.getTime() >= lower && d.getTime() <= upper ? true : false;
                    }

                    return false;
                }

                @Override
                public void describeTo(Description description) {
                    description.appendText("False if timestamp is out of range, true if in range");
                }
            });
        }
    }

    @Test
    public void writeRecording() throws Exception {

    }

    @Autowired
    public void setDynamoDbService(DynamoDBService dynamoDbService) {
        this.dynamoDbService = dynamoDbService;
    }
}