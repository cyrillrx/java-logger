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
public class ScheduledConsumer extends EventConsumer<Queue<TrackEvent>> {

    public ScheduledConsumer(TrackerChild tracker, Queue<TrackEvent> queue) {
        super(tracker, queue);
    }

    @Override
    public void run() {

        running = true;
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

        final List<TrackEvent> batch = new ArrayList<>();

        TrackEvent event;
        while (!events.isEmpty()) {
            event = events.poll();

            // If stop received, stop the batch and send pending events
            if (STOP_EVENT.equals(event)) {
                running = false;
                break;
            }
            batch.add(event);
        }

        try {
            doConsume(batch);

        } catch (Exception e) {
            // TODO implement retry policy
            events.addAll(batch);
        }
    }

    private synchronized void doConsume(List<TrackEvent> events) {
        tracker.track(events);
    }
}
