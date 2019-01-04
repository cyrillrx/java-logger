package com.cyrillrx.tracker;

import com.cyrillrx.tracker.event.TrackEvent;

/**
 * A tracker that fails to track the first event on purpose.
 *
 * @author Cyril Leroux
 *         Created on 26/04/2016.
 */
class RetryTracker extends DummyTrackerTracker {

    protected boolean fail;

    public RetryTracker() { fail = true; }

    @Override
    protected void doTrack(TrackEvent event) {
        if (fail) {
            fail = false;
            throw new RuntimeException("Failed tracking the event on purpose !");

        } else {
            super.doTrack(event);
        }
    }
}
