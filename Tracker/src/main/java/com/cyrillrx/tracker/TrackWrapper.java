package com.cyrillrx.tracker;

import com.cyrillrx.tracker.event.TrackEvent;

import java.util.Collection;

/**
 * A {@link TrackerChild} wrapper aware of the filter to apply.
 * If no filter is set, the com.cyrillrx.tracker tracks all events.
 *
 * @author Cyril Leroux
 *         Created on 11/11/15
 */
public abstract class TrackWrapper implements TrackerChild, TrackFilter {

    protected final TrackFilter filter;
    protected final TrackerChild nestedTracker;

    public TrackWrapper(TrackerChild tracker, TrackFilter filter) {
        this.filter = filter;
        nestedTracker = tracker;
    }

    public TrackWrapper(TrackerChild tracker) { this(tracker, null); }

    @Override
    public void track(TrackEvent event) {
        if (shouldTrack(event)) {
            doTrack(event);
        }
    }

    @Override
    public final void track(Collection<TrackEvent> events) {
        for (TrackEvent event : events) {
            track(event);
        }
    }

    @Override
    public boolean shouldTrack(TrackEvent event) { return filter == null || filter.shouldTrack(event); }

    protected void doTrack(TrackEvent event) { nestedTracker.track(event); }
}
