package com.cyrillrx.tracker;

import com.cyrillrx.tracker.event.TrackEvent;

import java.util.Collection;

/**
 * @author Cyril Leroux
 *         Created on 11/11/2015.
 */
public interface TrackerChild {

    void track(TrackEvent event);

    void track(Collection<TrackEvent> events);
}
