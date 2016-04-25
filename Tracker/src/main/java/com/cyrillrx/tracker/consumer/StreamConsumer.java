package com.cyrillrx.tracker.consumer;

import com.cyrillrx.tracker.TrackerChild;
import com.cyrillrx.tracker.event.TrackEvent;

import java.util.concurrent.BlockingQueue;

/**
 * @author Cyril Leroux
 *         Created on 20/04/16.
 */
public class StreamConsumer extends EventConsumer<BlockingQueue<TrackEvent>> {

    public StreamConsumer(TrackerChild tracker, BlockingQueue<TrackEvent> queue) { super(tracker, queue); }

    @Override
    public void run() {

        running = true;
        while (running) {
            consume();
        }
    }

    @Override
    protected void consume() {

        try {
            final TrackEvent event = events.take();

            if (STOP_EVENT.equals(event)) {
                running = false;
            }

            try {
                doConsume(event);
            } catch (Exception e) {
                // TODO implement retry policy
                events.add(event);
            }

        } catch (InterruptedException e) {
            running = false;
        }
    }

    private synchronized void doConsume(TrackEvent event) { tracker.track(event); }
}
