package com.cyrillrx.tracker

import com.cyrillrx.tracker.event.TrackEvent
import java.util.ArrayList

/**
 * A basic tracker the adds the tracked event to a list.
 *
 * @author Cyril Leroux
 *         Created on 22/04/2016.
 */
internal open class DummyStreamingTracker(capacity: Int, retryParams: RetryParams? = null) :
    StreamingTracker(capacity, retryParams) {

    val trackedEvents: ArrayList<TrackEvent> = ArrayList()

    override fun consumeEvent(event: TrackEvent) {
        trackedEvents.add(event)
    }

    fun isEmpty(): Boolean = trackedEvents.isEmpty()

    fun getEventCount(): Int = trackedEvents.size
}