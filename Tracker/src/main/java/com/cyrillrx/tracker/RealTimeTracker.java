package com.cyrillrx.tracker;

import com.cyrillrx.tracker.consumer.EventConsumer;
import com.cyrillrx.tracker.consumer.NamedThreadFactory;
import com.cyrillrx.tracker.event.TrackEvent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Cyril Leroux
 *         Created on 19/04/16.
 */
public class RealTimeTracker extends TrackWrapper {

    private static final String TAG = RealTimeTracker.class.getSimpleName();

    private static final String THREAD_PREFIX = TAG + "_";

    private final BlockingQueue<TrackEvent> queue;

    public RealTimeTracker(TrackerChild tracker, int capacity, int workerCount, TrackFilter filter) {
        super(tracker, filter);
        queue = new ArrayBlockingQueue<>(capacity, true);
        start(workerCount);
    }

    public RealTimeTracker(TrackerChild tracker, int capacity, int workerCount) {
        this(tracker, capacity, workerCount, null);
    }

    public RealTimeTracker(TrackerChild tracker, int capacity) {
        this(tracker, capacity, 1, null);
    }

    public void start(int workerCount) {

        final int processorCount = Runtime.getRuntime().availableProcessors();
        final ExecutorService service = Executors.newFixedThreadPool(
                processorCount,
                new NamedThreadFactory(THREAD_PREFIX));

        try {
            for (int i = 0; i < workerCount; ++i) {
                service.submit(new EventConsumer(queue));
            }

        } finally {
            service.shutdown();
        }
    }
}
