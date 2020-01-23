package com.cyrillrx.tracker

import com.cyrillrx.tracker.event.TrackEvent

import java.util.ArrayList
import java.util.concurrent.TimeUnit

/**
 * A basic tracker the adds the tracked event to a list.
 *
 * @author Cyril Leroux
 *         Created on 22/04/2016.
 */
internal class DummyScheduledTracker(intervalMillis: Long) : ScheduledTracker(intervalMillis, TimeUnit.MILLISECONDS) {

    val trackedEvents: ArrayList<TrackEvent> = ArrayList()

    override fun consumeEvents(events: Collection<TrackEvent>) {
        trackedEvents.addAll(events)
    }

    fun isEmpty(): Boolean = trackedEvents.isEmpty()

    fun getEventCount(): Int = trackedEvents.size
}
