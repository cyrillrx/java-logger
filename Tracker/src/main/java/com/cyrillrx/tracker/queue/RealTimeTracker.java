package com.cyrillrx.tracker.queue;

import com.cyrillrx.tracker.TrackFilter;
import com.cyrillrx.tracker.TrackerChild;
import com.cyrillrx.tracker.consumer.EventConsumer;
import com.cyrillrx.tracker.consumer.RealTimeConsumer;

/**
 * @author Cyril Leroux
 *         Created on 20/04/16.
 */
public class RealTimeTracker extends QueuedTracker {

    public RealTimeTracker(TrackerChild tracker, int capacity, int workerCount, TrackFilter filter) {
        super(tracker, capacity, workerCount, filter);
    }

    public RealTimeTracker(TrackerChild tracker, int capacity, int workerCount) {
        super(tracker, capacity, workerCount);
    }

    public RealTimeTracker(TrackerChild tracker, int capacity) {
        super(tracker, capacity);
    }

    @Override
    protected EventConsumer createConsumer() {
        return new RealTimeConsumer(wrapped, queue);
    }
}
