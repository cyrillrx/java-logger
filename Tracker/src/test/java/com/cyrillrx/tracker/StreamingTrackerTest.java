package com.cyrillrx.tracker;

import com.cyrillrx.tracker.queue.StreamingTracker;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Cyril Leroux
 *         Created on 20/04/16.
 */
public class StreamingTrackerTest {

    private static final int TRACE_COUNT = 10;
    private static final String CATEGORY_TEST = "test";

    @Test
    public void testConsistency() {

        final TestTracker logTracker = new TestTracker();
        final StreamingTracker tracker = new StreamingTracker.Builder()
                .setNestedTracker(logTracker)
                .setCapacity(10)
                .build();

        Assert.assertTrue("Should be empty", logTracker.getCategories().isEmpty());

        tracker.track(TestUtils.createFakeEvent(CATEGORY_TEST));
        TestUtils.wait100Millis();

        Assert.assertEquals("Should be 1", 1, logTracker.getCount());
        Assert.assertTrue("Should contain category " + CATEGORY_TEST, logTracker.categories.contains(CATEGORY_TEST));
    }

    @Test
    public void testOneByOne() {

        final TestTracker logTracker = new TestTracker();
        final StreamingTracker tracker = new StreamingTracker.Builder()
                .setNestedTracker(logTracker)
                .setCapacity(100)
                .build();

        Assert.assertTrue("Should be empty", logTracker.getCategories().isEmpty());

        // Adds TRACE_COUNT traces
        TestUtils.trackEventsOneByOne(tracker, TRACE_COUNT, CATEGORY_TEST);
        TestUtils.wait100Millis();
        Assert.assertEquals("Should be " + TRACE_COUNT, TRACE_COUNT, logTracker.getCount());


        // Adds TRACE_COUNT traces
        TestUtils.trackEventsOneByOne(tracker, TRACE_COUNT, CATEGORY_TEST);
        TestUtils.wait100Millis();
        int totalTraces = TRACE_COUNT * 2;
        Assert.assertEquals("Should be " + totalTraces, totalTraces, logTracker.getCount());
    }

    @Test
    public void testBatch() {

        System.out.println("Test started");

        final TestTracker logTracker = new TestTracker();
        final StreamingTracker tracker = new StreamingTracker.Builder()
                .setNestedTracker(logTracker)
                .setCapacity(100)
                .build();

        Assert.assertTrue("Should be empty", logTracker.getCategories().isEmpty());

        TestUtils.trackEventsBatch(tracker, TRACE_COUNT, CATEGORY_TEST);
        TestUtils.wait100Millis();
        Assert.assertEquals("Should be " + TRACE_COUNT, TRACE_COUNT, logTracker.getCount());

        TestUtils.trackEventsBatch(tracker, TRACE_COUNT, CATEGORY_TEST);
        TestUtils.wait100Millis();
        int totalTraces = TRACE_COUNT * 2;
        Assert.assertEquals("Should be " + totalTraces, totalTraces, logTracker.getCount());
    }


}
