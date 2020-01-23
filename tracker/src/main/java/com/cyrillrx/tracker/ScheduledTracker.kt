package com.cyrillrx.tracker

import com.cyrillrx.tracker.consumer.ScheduledConsumer
import com.cyrillrx.tracker.event.TrackEvent
import java.util.ArrayDeque
import java.util.Queue
import java.util.concurrent.TimeUnit

/**
 * @author Cyril Leroux
 *         Created on 22/04/2016.
 */
abstract class ScheduledTracker(private val timeDuration: Long, private val timeUnit: TimeUnit) :
    AsyncTracker<Queue<TrackEvent>>(ArrayDeque<TrackEvent>()) {

    override fun startConsuming() {
        val eventConsumer = object : ScheduledConsumer(pendingEvents, timeDuration) {
            override fun doConsume(events: List<TrackEvent>) {
                consumeEvents(events)
            }
        }
        eventConsumer.start()
    }

    protected abstract fun consumeEvents(events: Collection<TrackEvent>)
}