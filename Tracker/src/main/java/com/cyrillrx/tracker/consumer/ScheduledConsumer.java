package com.cyrillrx.tracker.consumer;

import com.cyrillrx.tracker.TrackerChild;
import com.cyrillrx.tracker.event.TrackEvent;
import com.cyrillrx.tracker.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * @author Cyril Leroux
 *         Created on 21/04/16.
 */
public class ScheduledConsumer extends EventConsumer<Queue<TrackEvent>> {

    private TimeUnit timeUnit;
    private long timeDuration;

    public ScheduledConsumer(TrackerChild tracker, Queue<TrackEvent> queue, TimeUnit unit, long duration) {
        super(tracker, queue);
        timeUnit = unit;
        timeDuration = duration;
    }

    @Override
    public void run() {

        running = true;
        while (running) {
            consume();
            Utils.wait(timeDuration, timeUnit);
        }
    }

    protected void consume() {

        // Events to be sent
        final List<TrackEvent> eventBucket = new ArrayList<>();

        TrackEvent event;
        while (!events.isEmpty()) {
            event = events.poll();

            // If stop received, stop the batch and send pending events
            if (STOP_EVENT.equals(event)) {
                running = false;
                break;
            }
            eventBucket.add(event);
        }

        try {
            doConsume(eventBucket);

        } catch (Exception e) {
            // TODO implement retry policy
            // TODO log warning
            events.addAll(eventBucket);
        }
    }

    private synchronized void doConsume(List<TrackEvent> events) { tracker.track(events); }
}
