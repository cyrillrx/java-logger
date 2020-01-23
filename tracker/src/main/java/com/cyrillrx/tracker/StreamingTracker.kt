package com.cyrillrx.tracker

import com.cyrillrx.logger.Logger
import com.cyrillrx.tracker.consumer.ScheduledConsumer
import com.cyrillrx.tracker.consumer.StreamingConsumer
import com.cyrillrx.tracker.event.TrackEvent
import java.util.Queue
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import java.util.concurrent.TimeUnit

/**
 * @author Cyril Leroux
 *         Created on 20/04/2016.
 */
abstract class StreamingTracker(
    capacity: Int,
    private val retryParams: RetryParams? = null
) : AsyncTracker<BlockingQueue<TrackEvent>>(createQueue(capacity)) {

    override fun startConsuming() {

        startEventConsumer()
        retryParams?.let(::startRetryQueueConsumer)
    }

    private fun startEventConsumer() {

        val eventConsumer = object : StreamingConsumer(pendingEvents) {

            override fun doConsume(event: TrackEvent) {
                consumeEvent(event)
            }

            override fun onDoConsumeFailure(event: TrackEvent, cause: Exception) {

                val retryQueue = retryParams?.queue
                if (retryQueue == null) {
                    super.onDoConsumeFailure(event, cause)

                } else {
                    Logger.info(TAG, "Error while consuming. Adding event to the retry queue.", cause)
                    retryQueue.add(event)
                }
            }
        }
        eventConsumer.start()
    }

    private fun startRetryQueueConsumer(retryParams: RetryParams) {

        // Set the retry consumer
        val retryQueueConsumer =
            object : ScheduledConsumer(retryParams.queue, retryParams.intervalValue, retryParams.intervalUnit) {
                override fun doConsume(events: List<TrackEvent>) {
                    pendingEvents.addAll(events)
                }
            }
        retryQueueConsumer.start()
    }

    protected abstract fun consumeEvent(event: TrackEvent)

    companion object {
        private val TAG = StreamingTracker::class.java.simpleName

        private fun createQueue(capacity: Int): BlockingQueue<TrackEvent> {
            return ArrayBlockingQueue(capacity, true)
        }
    }

    class RetryParams(
        val queue: Queue<TrackEvent>,
        val intervalValue: Long,
        val intervalUnit: TimeUnit
    )
}