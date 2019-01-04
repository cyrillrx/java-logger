package com.cyrillrx.tracker;

import com.cyrillrx.tracker.event.TrackEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * A basic tracker the adds the tracked event to a list.
 *
 * @author Cyril Leroux
 *         Created on 22/04/2016.
 */
class DummyTrackerTracker extends TrackerChild {

    public final List<TrackEvent> events = new ArrayList<>();

    DummyTrackerTracker() { }

    @Override
    protected void doTrack(TrackEvent event) { events.add(event); }

    int getEventCount() { return events.size(); }

    boolean isEmpty() { return events.isEmpty(); }
}
