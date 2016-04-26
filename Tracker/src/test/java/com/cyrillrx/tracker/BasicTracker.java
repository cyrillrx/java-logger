package com.cyrillrx.tracker;

import com.cyrillrx.tracker.event.TrackEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A basic tracker the adds the tracked event to a list.
 *
 * @author Cyril Leroux
 *         Created on 22/04/2016.
 */
class BasicTracker implements TrackerChild {

    private final List<TrackEvent> events;

    BasicTracker() { events = new ArrayList<>(); }

    @Override
    public void track(TrackEvent event) { events.add(event); }

    @Override
    public void track(Collection<TrackEvent> events) {
        for (TrackEvent event : events) {
            track(event);
        }
    }

    List<TrackEvent> getEvents() { return events; }

    int getEventCount() { return events.size(); }

    boolean isEmpty() { return events.isEmpty(); }
}
