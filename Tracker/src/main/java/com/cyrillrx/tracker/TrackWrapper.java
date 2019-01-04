package com.cyrillrx.tracker;

import com.cyrillrx.tracker.event.TrackEvent;

/**
 * A {@link TrackerChild} wrapper aware of the filter to apply.
 * If no filter is set, the tracker tracks all events.
 *
 * @author Cyril Leroux
 *         Created on 11/11/15
 */
public class TrackWrapper extends TrackerChild {

    protected final TrackFilter filter;
    protected final TrackerChild nestedTracker;

    public TrackWrapper(TrackerChild tracker, TrackFilter filter) {
        this.filter = filter;
        this.nestedTracker = tracker;
    }

    public TrackWrapper(TrackerChild tracker) { this(tracker, null); }

    @Override
    protected boolean shouldTrack(TrackEvent event) { return filter == null || filter.shouldTrack(event); }

    protected void doTrack(TrackEvent event) { nestedTracker.track(event); }
}
