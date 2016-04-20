package com.cyrillrx.tracker.consumer;

import com.cyrillrx.tracker.TrackerChild;
import com.cyrillrx.tracker.event.TrackEvent;

import java.util.concurrent.BlockingQueue;

/**
 * @author Cyril Leroux
 *         Created on 20/04/16.
 */
public abstract class EventConsumer implements Runnable {

    protected static final TrackEvent STOP_EVENT = new TrackEvent.Builder()
            .setCategory("STOP_EVENT")
            .build();

    protected final TrackerChild              tracker;
    protected final BlockingQueue<TrackEvent> eventQueue;

    protected boolean running;

    public EventConsumer(TrackerChild tracker, BlockingQueue<TrackEvent> queue) {
        this.tracker = tracker;
        this.eventQueue = queue;
        this.running = true;
    }

    protected boolean consume() {

        try {
            final TrackEvent event = eventQueue.take();

            if (STOP_EVENT.equals(event)) {
                running = false;
                return false;
            }

            // TODO implement retry policy
            return doConsume(event);

        } catch (InterruptedException e) {
            running = false;
            return false;
        }
    }

    private synchronized boolean doConsume(TrackEvent event) {
        tracker.track(event);
        return true;
    }
}
