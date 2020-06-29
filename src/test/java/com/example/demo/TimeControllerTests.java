package com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.time.LocalDateTime;
import java.util.TimeZone;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TimeControllerTests {
    @Autowired
    TimeController timeController;

    @Test
    public void testGetTime() {
        Time t = timeController.getCurrentTime();

        long referenceTime = new Date().getTime();
        long time = t.getTime().getTime();

        if (referenceTime - 1000 > time || referenceTime + 1000 < time) {
            Assert.fail("Max tolerance is 1000ms");
        }

        ZoneId referenceZone = ZoneId.systemDefault();
        String referenceCountry = Locale.getDefault().getCountry();

        Assert.assertEquals(referenceZone, t.getTimeZone());
        Assert.assertEquals(referenceCountry, t.getCountry());
    }
}