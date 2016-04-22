package com.cyrillrx.tracker;

import com.cyrillrx.tracker.event.TrackEvent;
import com.cyrillrx.tracker.queue.ScheduledTracker;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Cyril Leroux
 *         Created on 22/04/16.
 */
public class ScheduledTrackerTest {

    @Test
    public void testScheduledTracker() {

        System.out.println("Test started");

        final MockLogTracker logTracker = new MockLogTracker();
        final ScheduledTracker tracker = new ScheduledTracker(logTracker);

        Assert.assertTrue("Should be empty", logTracker.getCategories().isEmpty());

        tracker.track(new TrackEvent.Builder().setCategory("test").build());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            // TODO
            System.out.println(e);
        }

        Assert.assertEquals("Should be 1", 1, logTracker.getCount());
        Assert.assertTrue("Should contain test", logTracker.categories.contains("test"));
    }

}
