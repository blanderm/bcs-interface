package com.hopologybrewing.bcs.capture.aws.dynamo;

import com.hopologybrewing.bcs.capture.model.OutputRecording;
import com.hopologybrewing.bcs.capture.model.Recording;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        date.setTime(1485487408635L - 10L);
        long upper = date.getTime();
        long lower = upper - 8000;
        List<Recording> recordings = dynamoDbService.queryRecording(OutputRecording.class, "Pump - Left", lower, upper, 0, false);
        checkRange(recordings, lower, upper);

        recordings = dynamoDbService.queryRecording(OutputRecording.class, "Pump - Right", lower, upper, 0, false);
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