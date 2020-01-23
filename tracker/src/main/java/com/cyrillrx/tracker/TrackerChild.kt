package com.cyrillrx.tracker

import com.cyrillrx.tracker.event.TrackEvent

/**
 * A basic tracker aware of the filter to apply.<br></br>
 * By default, all events are tracked.
 *
 * @author Cyril Leroux
 *         Created on 11/11/2015.
 */
abstract class TrackerChild {

    fun track(event: TrackEvent) {
        if (shouldTrack(event)) {
            doTrack(event)
        }
    }

    fun track(events: Collection<TrackEvent>) {
        for (event in events) {
            track(event)
        }
    }

    protected fun shouldTrack(@Suppress("UNUSED_PARAMETER") event: TrackEvent): Boolean = true

    protected abstract fun doTrack(event: TrackEvent)
}