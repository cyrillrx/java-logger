package com.cyrillrx.tracker

import com.cyrillrx.tracker.event.TrackEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.sendBlocking
import java.util.Queue

/**
 * @author Cyril Leroux
 *         Created on 19/04/2016.
 */
abstract class AsyncTracker(protected var pendingEvents: Channel<TrackEvent>) : TrackerChild() {

    /** Adds the event to the queue where it will be consume. */
    override suspend fun doTrack(event: TrackEvent) {
        pendingEvents.send(event)
    }

    /** Starts the pending events consumer(s).  */
    abstract fun startConsuming()
}