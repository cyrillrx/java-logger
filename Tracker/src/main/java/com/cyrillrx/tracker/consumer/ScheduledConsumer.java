package com.cyrillrx.tracker.consumer;

import com.cyrillrx.tracker.TrackerChild;
import com.cyrillrx.tracker.event.TrackEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * @author Cyril Leroux
 *         Created on 21/04/16.
 */
public class ScheduledConsumer implements Runnable {

    protected static final TrackEvent STOP_EVENT = new TrackEvent.Builder()
            .setCategory("STOP_EVENT")
            .build();

    protected final TrackerChild      tracker;
    protected final Queue<TrackEvent> eventQueue;
    protected       boolean           running;

    public ScheduledConsumer(TrackerChild tracker, Queue<TrackEvent> queue) {
        this.tracker = tracker;
        this.eventQueue = queue;
        this.running = true;
    }

    @Override
    public void run() {

        while (running) {

            consume();

            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException ex) {
                running = false;
            }
        }
    }

    protected void consume() {

        final List<TrackEvent> events = new ArrayList<>();

        TrackEvent event;
        while (!eventQueue.isEmpty()) {
            event = eventQueue.poll();
            if (STOP_EVENT.equals(event)) {
                running = false;
                break;
            }
            events.add(event);
        }

        try {
            doConsume(events);

        } catch (Exception e) {
            // TODO retry
        }
    }

    private synchronized boolean doConsume(List<TrackEvent> events) {
        tracker.track(events);
        return true;
    }
}
