package com.cyrillrx.tracker.consumer;

import com.cyrillrx.tracker.TrackerChild;
import com.cyrillrx.tracker.event.TrackEvent;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Cyril Leroux
 *         Created on 20/04/16.
 */
public class StreamingConsumer extends EventConsumer<BlockingQueue<TrackEvent>> {

    protected Queue<TrackEvent> retryQueue;

    public StreamingConsumer(TrackerChild tracker, BlockingQueue<TrackEvent> queue, Queue<TrackEvent> retryQueue) {
        super(tracker, queue);
        this.retryQueue = retryQueue;
    }

    public StreamingConsumer(TrackerChild tracker, BlockingQueue<TrackEvent> queue) {
        this(tracker, queue, null);
    }

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

                if (retryQueue != null) {
                    retryQueue.add(event);
                } else {
                    // TODO Log : No retry implemented
                }
            }

        } catch (InterruptedException e) {
            running = false;
        }
    }

    private synchronized void doConsume(TrackEvent event) { tracker.track(event); }
}
