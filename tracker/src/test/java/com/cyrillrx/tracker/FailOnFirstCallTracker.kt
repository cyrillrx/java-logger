package com.cyrillrx.tracker

import com.cyrillrx.tracker.event.TrackEvent

/**
 * A tracker that fails to track the first event on purpose.
 *
 * @author Cyril Leroux
 *         Created on 26/04/2016.
 */
internal class FailOnFirstCallTracker(capacity: Int, retryParams: RetryParams) :
    DummyStreamingTracker(capacity, retryParams) {

    private var fail = true

    override fun consumeEvent(event: TrackEvent) {
        if (fail) {
            fail = false
            throw RuntimeException("Failed tracking the event on purpose !")
        } else {
            super.consumeEvent(event)
        }
    }
}