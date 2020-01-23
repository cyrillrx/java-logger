package com.cyrillrx.tracker.consumer

import com.cyrillrx.logger.Logger
import com.cyrillrx.tracker.event.TrackEvent
import com.cyrillrx.tracker.utils.waitFor
import java.util.ArrayList
import java.util.Queue
import java.util.concurrent.TimeUnit

/**
 * @author Cyril Leroux
 *         Created on 21/04/2016.
 */
abstract class ScheduledConsumer(
    queue: Queue<TrackEvent>,
    private val timeDuration: Long,
    private val timeUnit: TimeUnit
) : EventConsumer<Queue<TrackEvent>>(queue) {

    override fun consume() {

        // Events to be sent
        val pendingEvents: MutableList<TrackEvent> = ArrayList()

        var event: TrackEvent

        while (!events.isEmpty()) {
            event = events.poll() ?: break

            // If stop received, stop the batch and send pending events
            if (event == STOP_EVENT) {
                running = false
                break
            }
            pendingEvents.add(event)
        }

        try {
            doConsume(pendingEvents)
        } catch (cause: Exception) {
            Logger.info(TAG, "Error while consuming. Re-injecting events the event queue.", cause)
            events.addAll(pendingEvents)
        }

        waitFor(timeDuration, timeUnit)
    }

    protected abstract fun doConsume(events: List<TrackEvent>)

    companion object {
        private val TAG = ScheduledConsumer::class.java.simpleName
    }
}