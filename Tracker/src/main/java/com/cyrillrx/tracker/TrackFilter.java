package com.cyrillrx.tracker;

import com.cyrillrx.tracker.event.TrackEvent;

/**
 * @author Cyril Leroux
 *         Created on 11/11/2015.
 */
public interface TrackFilter {

    boolean shouldTrack(TrackEvent event);
}
