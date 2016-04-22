package com.cyrillrx.tracker.queue;

import com.cyrillrx.tracker.TrackFilter;
import com.cyrillrx.tracker.TrackerChild;
import com.cyrillrx.tracker.consumer.StreamConsumer;
import com.cyrillrx.tracker.event.TrackEvent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Cyril Leroux
 *         Created on 20/04/16.
 */
public class StreamingTracker extends AsyncTracker<BlockingQueue<TrackEvent>> {

    public StreamingTracker(TrackerChild tracker, int capacity, int workerCount, TrackFilter filter) {
        super(tracker, createQueue(capacity), workerCount, filter);
    }

    public StreamingTracker(TrackerChild tracker, int capacity, int workerCount) {
        super(tracker, createQueue(capacity), workerCount);
    }

    public StreamingTracker(TrackerChild tracker, int capacity) {
        super(tracker, createQueue(capacity), 1);
    }

    @Override
    protected StreamConsumer createConsumer() {
        return new StreamConsumer(wrapped, queue);
    }

    protected static BlockingQueue<TrackEvent> createQueue(int capacity) {
        return new ArrayBlockingQueue<>(capacity, true);
    }
}
