package com.cyrillrx.tracker;

import com.cyrillrx.tracker.event.TrackEvent;
import com.cyrillrx.tracker.queue.StreamingTracker;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Cyril Leroux
 *         Created on 20/04/16.
 */
public class StreamingTrackerTest {

    @Test
    public void testSteamingTracker() {

        System.out.println("Test started");

        final MockLogTracker logTracker = new MockLogTracker();
        final StreamingTracker tracker = new StreamingTracker.Builder()
                .setNestedTracker(logTracker)
                .setCapacity(200)
                .build();

        Assert.assertTrue("Should be empty", logTracker.getCategories().isEmpty());

        tracker.track(new TrackEvent.Builder().setCategory("test").build());
        tracker.track(new TrackEvent.Builder().setCategory("test").build());
        Utils.wait(1, TimeUnit.SECONDS);

        Assert.assertEquals("Should be 2", 2, logTracker.getCount());

        tracker.track(new TrackEvent.Builder().setCategory("test").build());
        tracker.track(new TrackEvent.Builder().setCategory("test").build());
        Utils.wait(1, TimeUnit.SECONDS);

        Assert.assertEquals("Should be 4", 4, logTracker.getCount());
        Assert.assertTrue("Should contain test", logTracker.categories.contains("test"));
    }
}
