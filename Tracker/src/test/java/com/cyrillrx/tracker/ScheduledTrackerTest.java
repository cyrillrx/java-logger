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
        final ScheduledTracker tracker = new ScheduledTracker.Builder()
                .setNestedTracker(logTracker)
                .setInterval(5, TimeUnit.SECONDS)
                .build();

        Utils.wait(1, TimeUnit.SECONDS);

        Assert.assertTrue("Should be empty", logTracker.getCategories().isEmpty());

        tracker.track(new TrackEvent.Builder().setCategory("test").build());
        tracker.track(new TrackEvent.Builder().setCategory("test").build());
        tracker.track(new TrackEvent.Builder().setCategory("test").build());
        tracker.track(new TrackEvent.Builder().setCategory("test").build());
        tracker.track(new TrackEvent.Builder().setCategory("test").build());

        Assert.assertTrue("Should be empty", logTracker.getCategories().isEmpty());

        Utils.wait(1, TimeUnit.SECONDS);

        Assert.assertTrue("Should be empty", logTracker.getCategories().isEmpty());

        Utils.wait(5, TimeUnit.SECONDS);

        Assert.assertEquals("Should be 5", 5, logTracker.getCount());
        Assert.assertTrue("Should contain test", logTracker.categories.contains("test"));
    }

}
