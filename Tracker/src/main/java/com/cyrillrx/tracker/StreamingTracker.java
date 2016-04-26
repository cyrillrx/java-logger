package com.cyrillrx.tracker;

import com.cyrillrx.tracker.consumer.ScheduledConsumer;
import com.cyrillrx.tracker.consumer.StreamingConsumer;
import com.cyrillrx.tracker.event.TrackEvent;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Cyril Leroux
 *         Created on 20/04/16.
 */
public class StreamingTracker extends AsyncTracker<BlockingQueue<TrackEvent>> {

    private Queue<TrackEvent> retryQueue;
    protected TimeUnit retryUnit;
    protected long retryInterval;

    protected StreamingTracker(TrackerChild tracker, int capacity, TrackFilter filter) {
        super(tracker, createQueue(capacity), filter);
    }

    @Override
    protected void submitToService(ExecutorService service) {
        super.submitToService(service);

        // Set the retry consumer
        if (retryQueue != null && retryUnit != null && retryInterval > 0L) {
            service.submit(new ScheduledConsumer(nestedTracker, retryQueue, retryUnit, retryInterval));
        }
    }

    @Override
    protected StreamingConsumer createConsumer() {
        return new StreamingConsumer(nestedTracker, queue, retryQueue);
    }

    private static BlockingQueue<TrackEvent> createQueue(int capacity) {
        return new ArrayBlockingQueue<>(capacity, true);
    }

    public static class Builder {

        private TrackerChild nestedTracker;
        private TrackFilter filter;

        private int workerCount;
        private int capacity;

        private Queue<TrackEvent> retryQueue;
        private TimeUnit retryUnit;
        private long retryInterval;

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

        public Builder setRetry(Queue<TrackEvent> queue, TimeUnit unit, long interval) {
            this.retryQueue = queue;
            this.retryUnit = unit;
            this.retryInterval = interval;
            return this;
        }

        public StreamingTracker build() {

            final StreamingTracker wrapper = new StreamingTracker(nestedTracker, capacity, filter);

            wrapper.retryQueue = retryQueue;
            wrapper.retryUnit = retryUnit;
            wrapper.retryInterval = retryInterval;

            wrapper.start(workerCount);
            return wrapper;
        }
    }
}
