package com.cyrillrx.android.tracker;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cyrillrx.android.tracker.event.TrackEvent;

/**
 * A {@link TrackerChild} wrapper aware of the filter to apply.
 *
 * @author Cyril Leroux
 *         Created on 11/11/15
 */
public abstract class TrackWrapper implements TrackerChild {

    private final TrackFilter  mFilter;
    private final TrackerChild mWrapped;

    public TrackWrapper(@NonNull TrackerChild tracker, TrackFilter filter) {
        mFilter = filter;
        mWrapped = tracker;
    }

    @Override
    public void track(Context context, TrackEvent event) {
        if (mFilter.shouldTrack(event)) {
            mWrapped.track(context, event);
        }
    }
}
