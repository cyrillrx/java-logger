package com.cyrillrx.tracker

import com.cyrillrx.tracker.event.TrackEvent
import java.util.Queue

/**
 * @author Cyril Leroux
 *         Created on 19/04/2016.
 */
abstract class AsyncTracker<EventQueue : Queue<TrackEvent>>(protected var pendingEvents: EventQueue) : TrackerChild() {

    /** Adds the event to the queue where it will be consume. */
    override fun doTrack(event: TrackEvent) {
        pendingEvents.add(event)
    }

    /** Starts the pending events consumer(s).  */
    abstract fun startConsuming()
}