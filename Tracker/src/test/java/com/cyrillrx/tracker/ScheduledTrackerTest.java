package com.cyrillrx.tracker;

import com.cyrillrx.tracker.queue.ScheduledTracker;
import com.cyrillrx.tracker.utils.Utils;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Cyril Leroux
 *         Created on 22/04/16.
 */
public class ScheduledTrackerTest {

    private static final int TRACE_COUNT = 10;
    private static final int INTERVAL = 2;
    private static final String CATEGORY_TEST = "test";

    @Test
    public void testScheduledTracker() {

        System.out.println("Test started");

        final TestTracker logTracker = new TestTracker();
        final ScheduledTracker tracker = new ScheduledTracker.Builder()
                .setNestedTracker(logTracker)
                .setInterval(INTERVAL, TimeUnit.SECONDS)
                .build();

        // Wait until the tracker actually starts tracking
        TestUtils.wait100Millis();

        Assert.assertTrue("Should be empty", logTracker.getCategories().isEmpty());

        // Adds TRACE_COUNT traces
        TestUtils.trackEventsOneByOne(tracker, TRACE_COUNT, CATEGORY_TEST);
        // Check emptiness
        Assert.assertTrue("Should be empty", logTracker.getCategories().isEmpty());

        // Wait then check again
        TestUtils.wait100Millis();
        Assert.assertTrue("Should be empty", logTracker.getCategories().isEmpty());

        // Wait enough time for the traces to be consumed
        Utils.wait(INTERVAL, TimeUnit.SECONDS);

        // Check that the traces were sent
        Assert.assertEquals("Should be " + TRACE_COUNT, TRACE_COUNT, logTracker.getCount());
        Assert.assertTrue("Should contain category " + CATEGORY_TEST, logTracker.categories.contains(CATEGORY_TEST));
    }

}
