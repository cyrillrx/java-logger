package com.cyrillrx.tracker.queue;

import com.cyrillrx.tracker.TrackFilter;
import com.cyrillrx.tracker.TrackWrapper;
import com.cyrillrx.tracker.TrackerChild;
import com.cyrillrx.tracker.consumer.EventConsumer;
import com.cyrillrx.tracker.consumer.NamedThreadFactory;
import com.cyrillrx.tracker.event.TrackEvent;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Cyril Leroux
 *         Created on 19/04/16.
 */
public abstract class AsyncTracker<EventQueue extends Queue<TrackEvent>>
        extends TrackWrapper {

    private static final String TAG = AsyncTracker.class.getSimpleName();

    private static final String THREAD_PREFIX = TAG + "_";
    protected static final int DEFAULT_WORKER_COUNT = 1;

    protected EventQueue queue;

    public AsyncTracker(TrackerChild tracker, EventQueue queue, TrackFilter filter) {
        super(tracker, filter);
        this.queue = queue;
    }

    public AsyncTracker(TrackerChild tracker, EventQueue queue) { this(tracker, queue, null); }

    /**
     * Adds the event to the queue where it will be consume.
     *
     * @param event The event to add to the queue.
     */
    @Override
    protected void doTrack(TrackEvent event) { queue.add(event); }

    /**
     * Starts the event queue consumers.
     *
     * @param workerCount
     */
    protected void start(int workerCount) {

        final int processorCount = Runtime.getRuntime().availableProcessors();
        final ExecutorService service = Executors.newFixedThreadPool(
                processorCount,
                new NamedThreadFactory(THREAD_PREFIX));

        try {
            for (int i = 0; i < workerCount; ++i) {
                service.submit(createConsumer());
            }

        } finally {
            service.shutdown();
        }
    }

    protected abstract EventConsumer<EventQueue> createConsumer();
}
