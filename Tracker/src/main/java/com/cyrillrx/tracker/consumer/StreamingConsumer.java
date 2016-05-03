package com.cyrillrx.tracker.consumer;

import com.cyrillrx.logger.Logger;
import com.cyrillrx.tracker.TrackerChild;
import com.cyrillrx.tracker.event.TrackEvent;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Cyril Leroux
 *         Created on 20/04/16.
 */
public class StreamingConsumer extends EventConsumer<BlockingQueue<TrackEvent>> {

    private static final String TAG = StreamingConsumer.class.getSimpleName();

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

                if (retryQueue == null) {
                    Logger.error(TAG, "Error while consuming the event without a retry queue. Rethrowing exception", e);
                    throw e;

                } else {
                    Logger.info(TAG, "Error while consuming. Adding event to the retry queue.", e);
                    retryQueue.add(event);
                }
            }

        } catch (InterruptedException e) {
            running = false;
        }
    }

    private synchronized void doConsume(TrackEvent event) { tracker.track(event); }
}
