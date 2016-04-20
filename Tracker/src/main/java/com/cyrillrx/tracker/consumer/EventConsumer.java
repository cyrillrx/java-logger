package com.cyrillrx.tracker.consumer;

import com.cyrillrx.tracker.event.TrackEvent;

import java.util.concurrent.BlockingQueue;

/**
 * @author Cyril Leroux
 *         Created on 20/04/16.
 */
public class EventConsumer implements Runnable {

    private static final String TAG = EventConsumer.class.getSimpleName();

    private final BlockingQueue<TrackEvent> queue;

    public static final TrackEvent STOP_EVENT = new TrackEvent.Builder()
            .setCategory("STOP_EVENT")
            .build();

    private boolean running;

    public EventConsumer(BlockingQueue<TrackEvent> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        while (running) {
            consume();
        }
    }

    private void consume() {

        try {
            final TrackEvent event = queue.take();

            if (STOP_EVENT.equals(event)) {
                running = false;
                return;
            }

            doConsume(event);

        } catch (InterruptedException e) {
            running = false;
        }
    }

    private synchronized void doConsume(TrackEvent event) {

    }
}
