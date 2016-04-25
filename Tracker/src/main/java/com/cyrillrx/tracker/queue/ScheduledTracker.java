package com.cyrillrx.tracker.queue;

import com.cyrillrx.tracker.TrackFilter;
import com.cyrillrx.tracker.TrackerChild;
import com.cyrillrx.tracker.consumer.ScheduledConsumer;
import com.cyrillrx.tracker.event.TrackEvent;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * @author Cyril Leroux
 *         Created on 22/04/16.
 */
public class ScheduledTracker extends AsyncTracker<Queue<TrackEvent>> {

    protected TimeUnit timeUnit;
    protected long timeDuration;

    protected ScheduledTracker(TrackerChild tracker, TrackFilter filter) { super(tracker, createQueue(), filter); }

    private static Queue<TrackEvent> createQueue() { return new ArrayDeque<>(); }

    @Override
    protected ScheduledConsumer createConsumer() {
        return new ScheduledConsumer(nestedTracker, queue, timeUnit, timeDuration);
    }

    public static class Builder {

        private TrackerChild nestedTracker;
        private TrackFilter filter;

        private TimeUnit timeUnit;
        private long timeDuration;

        private int workerCount;

        public Builder() {
            workerCount = DEFAULT_WORKER_COUNT;
            timeUnit = TimeUnit.SECONDS;
            timeDuration = 30;
        }

        public Builder setNestedTracker(TrackerChild tracker) {
            nestedTracker = tracker;
            return this;
        }

        public Builder setFilter(TrackFilter filter) {
            this.filter = filter;
            return this;
        }

        public Builder setInterval(long timeDuration, TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            this.timeDuration = timeDuration;
            return this;
        }

        public Builder setWorkerCount(int workerCount) {
            this.workerCount = workerCount;
            return this;
        }

        public ScheduledTracker build() {

            final ScheduledTracker wrapper = new ScheduledTracker(nestedTracker, filter);
            wrapper.timeUnit = timeUnit;
            wrapper.timeDuration = timeDuration;

            wrapper.start(workerCount);
            return wrapper;
        }
    }
}
