package com.cyrillrx.tracker.queue;

import com.cyrillrx.tracker.TrackFilter;
import com.cyrillrx.tracker.TrackerChild;
import com.cyrillrx.tracker.consumer.ScheduledConsumer;
import com.cyrillrx.tracker.event.TrackEvent;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author Cyril Leroux
 *         Created on 22/04/16.
 */
public class ScheduledTracker extends AsyncTracker<Queue<TrackEvent>> {

    public ScheduledTracker(TrackerChild tracker, int workerCount, TrackFilter filter) {
        super(tracker, createQueue(), workerCount, filter);
    }

    public ScheduledTracker(TrackerChild tracker, int workerCount) {
        super(tracker, createQueue(), workerCount);
    }

    public ScheduledTracker(TrackerChild tracker) {
        super(tracker, createQueue());
    }

    @Override
    protected ScheduledConsumer createConsumer() {
        return new ScheduledConsumer(wrapped, queue);
    }

    protected static Queue<TrackEvent> createQueue() {
        return new ArrayDeque<>();
    }
}
