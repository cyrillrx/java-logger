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

    protected StreamingTracker(TrackerChild tracker, int capacity, TrackFilter filter) {
        super(tracker, createQueue(capacity), filter);
    }

    private static BlockingQueue<TrackEvent> createQueue(int capacity) {
        return new ArrayBlockingQueue<>(capacity, true);
    }

    @Override
    protected StreamConsumer createConsumer() { return new StreamConsumer(nestedTracker, queue); }

    public static class Builder {

        private TrackerChild nestedTracker;
        private TrackFilter filter;

        private int workerCount;
        private int capacity;

        public Builder() { workerCount = DEFAULT_WORKER_COUNT; }

        public Builder setNestedTracker(TrackerChild tracker) {
            nestedTracker = tracker;
            return this;
        }

        public Builder setFilter(TrackFilter filter) {
            this.filter = filter;
            return this;
        }

        public Builder setCapacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder setWorkerCount(int workerCount) {
            this.workerCount = workerCount;
            return this;
        }

        public StreamingTracker build() {

            final StreamingTracker wrapper = new StreamingTracker(nestedTracker, capacity, filter);
            wrapper.start(workerCount);
            return wrapper;
        }
    }
}
