package com.cyrillrx.tracker.consumer;

import com.cyrillrx.tracker.TrackerChild;
import com.cyrillrx.tracker.event.TrackEvent;

import java.util.concurrent.BlockingQueue;

/**
 * @author Cyril Leroux
 *         Created on 20/04/16.
 */
public class RealTimeConsumer extends EventConsumer {

    public RealTimeConsumer(TrackerChild tracker, BlockingQueue<TrackEvent> queue) {
        super(tracker, queue);
    }

    @Override
    public void run() {

        while (running) {
            consume();
        }
    }
}
