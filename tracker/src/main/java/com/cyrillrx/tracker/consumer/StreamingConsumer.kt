package com.cyrillrx.tracker.consumer

import com.cyrillrx.logger.Logger
import com.cyrillrx.tracker.event.TrackEvent
import java.util.concurrent.BlockingQueue

/**
 * @author Cyril Leroux
 *         Created on 20/04/2016.
 */
abstract class StreamingConsumer(queue: BlockingQueue<TrackEvent>) : EventConsumer<BlockingQueue<TrackEvent>>(queue) {

    override fun consume() {

        try {
            val event = events.take()
            if (event == STOP_EVENT) {
                running = false
            }

            try {
                doConsume(event)
            } catch (e: Exception) {
                onDoConsumeFailure(event, e)
            }

        } catch (e: InterruptedException) {
            running = false
        }
    }

    protected abstract fun doConsume(event: TrackEvent)

    protected open fun onDoConsumeFailure(event: TrackEvent, cause: Exception) {
        Logger.error(TAG, "Error while consuming the event without a retry queue. Rethrowing exception", cause)
        throw cause
    }

    companion object {
        private val TAG = StreamingConsumer::class.java.simpleName
    }
}