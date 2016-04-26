package com.cyrillrx.tracker;

import com.cyrillrx.tracker.event.TrackEvent;
import com.cyrillrx.tracker.utils.Utils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Cyril Leroux
 *         Created on 22/04/16.
 */
public class ScheduledTrackerTest {

    private static final int TRACE_COUNT = 10;
    private static final int INTERVAL = 200;
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;

    @Test
    public void testConsistency() {

        System.out.println("Test started");

        final BasicTracker nestedTracker = new BasicTracker();
        final ScheduledTracker tracker = new ScheduledTracker.Builder()
                .setNestedTracker(nestedTracker)
                .setInterval(INTERVAL, UNIT)
                .build();

        // Wait until the tracker actually starts tracking
        TestUtils.wait100Millis();

        Assert.assertTrue("Should be empty", nestedTracker.isEmpty());

        // Track one event
        tracker.track(TestUtils.createFakeEvent(TestUtils.EVENT_CATEGORY));

        // Assert emptiness
        Assert.assertTrue("Should be empty", nestedTracker.isEmpty());

        // Wait enough time for the traces to be consumed
        Utils.wait(INTERVAL, UNIT);

        // Assert that the traces were sent
        Assert.assertEquals("Should be 1", 1, nestedTracker.getEventCount());

        final List<TrackEvent> trackedEvents = nestedTracker.getEvents();
        for (TrackEvent event : trackedEvents) {
            Assert.assertTrue("Category " + TestUtils.EVENT_CATEGORY, TestUtils.EVENT_CATEGORY.equals(event.getCategory()));
        }
    }

    @Test
    public void testOneByOne() {

        final BasicTracker nestedTracker = new BasicTracker();
        final ScheduledTracker tracker = new ScheduledTracker.Builder()
                .setNestedTracker(nestedTracker)
                .setInterval(INTERVAL, UNIT)
                .build();

        // Wait until the tracker actually starts tracking
        TestUtils.wait100Millis();

        Assert.assertTrue("Should be empty", nestedTracker.isEmpty());

        // Track TRACE_COUNT events
        TestUtils.trackEventsOneByOne(tracker, TRACE_COUNT, TestUtils.EVENT_CATEGORY);

        // Assert emptiness
        Assert.assertTrue("Should be empty", nestedTracker.isEmpty());

        // Wait enough time for the events to be consumed
        Utils.wait(INTERVAL, UNIT);

        // Assert that the events were sent
        Assert.assertEquals("Should be " + TRACE_COUNT, TRACE_COUNT, nestedTracker.getEventCount());
    }

    @Test
    public void testBatch() {

        final BasicTracker nestedTracker = new BasicTracker();
        final ScheduledTracker tracker = new ScheduledTracker.Builder()
                .setNestedTracker(nestedTracker)
                .setInterval(INTERVAL, UNIT)
                .build();

        // Wait until the tracker actually starts tracking
        TestUtils.wait100Millis();

        Assert.assertTrue("Should be empty", nestedTracker.isEmpty());

        // Track TRACE_COUNT events
        TestUtils.trackEventsBatch(tracker, TRACE_COUNT, TestUtils.EVENT_CATEGORY);

        // Assert emptiness
        Assert.assertTrue("Should be empty", nestedTracker.isEmpty());

        // Wait enough time for the events to be consumed
        Utils.wait(INTERVAL, UNIT);

        // Assert that the events were sent
        Assert.assertEquals("Should be " + TRACE_COUNT, TRACE_COUNT, nestedTracker.getEventCount());
    }

}
