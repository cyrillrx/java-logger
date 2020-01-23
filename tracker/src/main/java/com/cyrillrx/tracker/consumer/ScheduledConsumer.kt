package com.cyrillrx.tracker.consumer

import com.cyrillrx.logger.Logger
import com.cyrillrx.tracker.event.TrackEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import java.util.ArrayList

/**
 * @author Cyril Leroux
 *         Created on 21/04/2016.
 */
abstract class ScheduledConsumer(
    pendingEvents: Channel<TrackEvent>,
    private val intervalMillis: Long
) :
    EventConsumer(pendingEvents) {

    override suspend fun consume() {

        val eventBatch = events.getAsBatch()

        try {
            doConsume(eventBatch)

        } catch (cause: Exception) {
            Logger.info(TAG, "Error while consuming. Re-injecting events the event queue.", cause)
            eventBatch.forEach { events.send(it) }
        }

        delay(intervalMillis)
    }

    protected abstract fun doConsume(events: List<TrackEvent>)

    private fun Channel<TrackEvent>.getAsBatch(): ArrayList<TrackEvent> {

        val eventBatch = ArrayList<TrackEvent>()

        var event: TrackEvent

        while (!isEmpty) {

            event = poll() ?: break

            // If stop received, stop the batch and send pending events
            if (event == STOP_EVENT) {
                running = false
                break
            }
            eventBatch.add(event)
        }

        return eventBatch
    }

    companion object {
        private val TAG = ScheduledConsumer::class.java.simpleName
    }
}