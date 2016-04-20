package com.cyrillrx.tracker;

import com.cyrillrx.tracker.event.TrackEvent;
import com.cyrillrx.tracker.queue.QueuedTracker;
import com.cyrillrx.tracker.queue.RealTimeTracker;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cyril Leroux
 *         Created on 20/04/16.
 */
public class TestSuite {

    @Test
    public void testRealTimeTracker() {

        System.out.println("Test started");

        final LogTracker logTracker = new LogTracker();
        final RealTimeTracker tracker = new RealTimeTracker(logTracker, 200);

        Assert.assertTrue("Should be empty", logTracker.getCategories().isEmpty());

        tracker.track(new TrackEvent.Builder().setCategory("test").build());

        Assert.assertEquals("Should be 1", 1, logTracker.getCount());
    }

    private class LogTracker implements TrackerChild {

        final List<String> categories = new ArrayList<>();

        @Override
        public void track(TrackEvent event) {

            categories.add(event.getCategory());
            System.out.println(event.getCategory());
        }

        public List<String> getCategories() { return categories; }

        private int getCount() { return categories.size(); }
    }
}
