package com.cyrillrx.tracker

import com.cyrillrx.tracker.event.TrackEvent
import com.cyrillrx.tracker.utils.waitFor
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * @author Cyril Leroux
 *         Created on 22/04/2016.
 */
class ScheduledTrackerTest {

    @Test
    fun testConsistency() {

        // Init tracker
        val tracker = DummyScheduledTracker(INTERVAL)
            .also { it.startConsuming() }

        // Wait until the tracker actually starts tracking
        TestUtils.wait100Millis()

        Assert.assertTrue("Should be empty", tracker.isEmpty())

        // Track one event
        tracker.track(TestUtils.createFakeEvent(TestUtils.EVENT_CATEGORY))

        // Assert emptiness
        Assert.assertTrue("Should be empty", tracker.isEmpty())

        // Wait enough time for the events to be consumed
        waitFor(INTERVAL, UNIT)

        // Assert that the event was sent

        Assert.assertEquals("Should be 1", 1, tracker.getEventCount())

        val trackedEvents: List<TrackEvent> = tracker.trackedEvents
        for (event in trackedEvents) {
            Assert.assertEquals("Category ${TestUtils.EVENT_CATEGORY}", TestUtils.EVENT_CATEGORY, event.category)
        }
    }

    @Test
    fun testOneByOne() = runBlocking {

        // Init tracker
        val tracker = DummyScheduledTracker(INTERVAL)
            .also { it.startConsuming() }

        // Wait until the tracker actually starts tracking
        TestUtils.wait100Millis()

        Assert.assertTrue("Should be empty", tracker.isEmpty())

        // Track EVENT_COUNT events
        TestUtils.trackEventsOneByOne(tracker, EVENT_COUNT, TestUtils.EVENT_CATEGORY)

        // Assert emptiness
        Assert.assertTrue("Should be empty", tracker.isEmpty())

        // Wait enough time for the events to be consumed
        waitFor(INTERVAL, UNIT)

        // Assert that the events were sent
        Assert.assertEquals("Should be $EVENT_COUNT", EVENT_COUNT, tracker.getEventCount())
    }

    @Test
    fun testBatch() = runBlocking {

        // Init tracker
        val tracker = DummyScheduledTracker(INTERVAL)
            .also { it.startConsuming() }

        // Wait until the tracker actually starts tracking
        TestUtils.wait100Millis()

        Assert.assertTrue("Should be empty", tracker.isEmpty())

        // Track EVENT_COUNT events
        TestUtils.trackEventsBatch(tracker, EVENT_COUNT, TestUtils.EVENT_CATEGORY)

        // Assert emptiness
        Assert.assertTrue("Should be empty", tracker.isEmpty())

        // Wait enough time for the events to be consumed
        waitFor(INTERVAL, UNIT)

        // Assert that the events were sent
        Assert.assertEquals("Should be $EVENT_COUNT", EVENT_COUNT, tracker.getEventCount())
    }

    companion object {
        private const val EVENT_COUNT = 10
        private const val INTERVAL = 200L
        private val UNIT = TimeUnit.MILLISECONDS
    }
}