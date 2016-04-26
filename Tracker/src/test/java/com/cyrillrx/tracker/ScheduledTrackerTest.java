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

    private static final int EVENT_COUNT = 10;
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

        // Wait enough time for the events to be consumed
        Utils.wait(INTERVAL, UNIT);

        // Assert that the event was sent
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

        // Track EVENT_COUNT events
        TestUtils.trackEventsOneByOne(tracker, EVENT_COUNT, TestUtils.EVENT_CATEGORY);

        // Assert emptiness
        Assert.assertTrue("Should be empty", nestedTracker.isEmpty());

        // Wait enough time for the events to be consumed
        Utils.wait(INTERVAL, UNIT);

        // Assert that the events were sent
        Assert.assertEquals("Should be " + EVENT_COUNT, EVENT_COUNT, nestedTracker.getEventCount());
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

        // Track EVENT_COUNT events
        TestUtils.trackEventsBatch(tracker, EVENT_COUNT, TestUtils.EVENT_CATEGORY);

        // Assert emptiness
        Assert.assertTrue("Should be empty", nestedTracker.isEmpty());

        // Wait enough time for the events to be consumed
        Utils.wait(INTERVAL, UNIT);

        // Assert that the events were sent
        Assert.assertEquals("Should be " + EVENT_COUNT, EVENT_COUNT, nestedTracker.getEventCount());
    }

}
