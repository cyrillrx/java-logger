package com.cyrillrx.tracker;

import com.cyrillrx.tracker.event.TrackEvent;

/**
 * @author Cyril Leroux
 *         Created on 20/04/16.
 */
public class TestSuite {

    @Test
    public void testRealTimeTracker() {

        // MyClass is tested
        RealTimeTracker tracker = new RealTimeTracker(new LogTracker(), 200);

        TrackEvent event = new TrackEvent.Builder().setCategory("test").build();
        tracker.track(event);
    }

    private class LogTracker implements TrackerChild {

        @Override
        public void track(TrackEvent event) {
            System.out.println(event.getCategory());
        }
    }
}
