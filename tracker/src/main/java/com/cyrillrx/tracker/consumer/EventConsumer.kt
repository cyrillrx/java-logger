package com.cyrillrx.tracker.consumer

import com.cyrillrx.tracker.event.TrackEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import java.util.Queue

/**
 * @author Cyril Leroux
 *         Created on 20/04/2016.
 */
abstract class EventConsumer(protected val events: Channel<TrackEvent>) {

    val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    var currentJob: Job? = null

    protected var running = false

    fun start(): Job = currentJob ?: startNewJob().also { currentJob = it }

    suspend fun stop(): Boolean {

        if (!running) return false
        val job = currentJob ?: return false
        if (job.isCancelled || job.isCompleted) return false

        events.send(STOP_EVENT)

        return true
    }

    private fun startNewJob(): Job = coroutineScope.launch {
        running = true
        while (running) {
            consume()
        }
    }

    protected abstract suspend fun consume()

    companion object {

        internal val STOP_EVENT = TrackEvent.Builder()
            .setCategory("STOP_EVENT")
            .setName("STOP_EVENT")
            .build()
    }
}