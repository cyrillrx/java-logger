package com.cyrillrx.tracker.consumer;

import com.cyrillrx.tracker.TrackerChild;
import com.cyrillrx.tracker.event.TrackEvent;

import java.util.Collection;

/**
 * @author Cyril Leroux
 *         Created on 20/04/16.
 */
public abstract class EventConsumer<EventContainer extends Collection<TrackEvent>>
        implements Runnable {

    protected static final TrackEvent STOP_EVENT = new TrackEvent.Builder()
            .setCategory("STOP_EVENT")
            .build();

    protected final TrackerChild tracker;
    protected final EventContainer events;
    protected boolean running;

    public EventConsumer(TrackerChild tracker, EventContainer events) {
        this.tracker = tracker;
        this.events = events;
    }

    protected abstract void consume();
}
