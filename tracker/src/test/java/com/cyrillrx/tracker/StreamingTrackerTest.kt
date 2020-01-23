package com.cyrillrx.tracker

import com.cyrillrx.logger.Logger
import com.cyrillrx.logger.Severity
import com.cyrillrx.logger.extension.SystemOutLog
import com.cyrillrx.tracker.event.TrackEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import java.util.ArrayDeque
import java.util.concurrent.TimeUnit

/**
 * @author Cyril Leroux
 *         Created on 20/04/2016.
 */
class StreamingTrackerTest {

    @Test
    fun testConsistency() = runBlocking {

        // Init tracker
        val tracker = DummyStreamingTracker(10)
            .also { it.startConsuming() }

        Assert.assertTrue("Should be empty", tracker.isEmpty())

        // Track one event
        val eventCategory = TestUtils.EVENT_CATEGORY
        tracker.track(TestUtils.createFakeEvent(eventCategory))
        delay(10L)

        Assert.assertEquals("Should be 1", 1, tracker.getEventCount())

        val trackedEvents: List<TrackEvent> = tracker.trackedEvents
        for (event in trackedEvents) {
            Assert.assertEquals("Should contain category $eventCategory", eventCategory, event.category)
        }
    }

    @Test
    fun testRetry() = runBlocking {

        // Init tracker
        val retryParams = StreamingTracker.RetryParams(ArrayDeque(), 200, TimeUnit.MILLISECONDS)
        val tracker = FailOnFirstCallTracker(10, retryParams)
            .also { it.startConsuming() }

        Assert.assertTrue("Should be empty", tracker.isEmpty())

        // First track should fail
        tracker.track(TestUtils.createFakeEvent(TestUtils.EVENT_CATEGORY))

        Assert.assertTrue("Should be empty", tracker.isEmpty())

        // Wait for retry to be performed
        delay(210)
        Assert.assertEquals("Should be 1", 1, tracker.getEventCount())
    }

    @Test
    fun testOneByOne() = runBlocking {

        // Init tracker
        val tracker = DummyStreamingTracker(100)
            .also { it.startConsuming() }

        Assert.assertTrue("Should be empty", tracker.isEmpty())

        // Track EVENT_COUNT events
        TestUtils.trackEventsOneByOne(tracker, EVENT_COUNT, TestUtils.EVENT_CATEGORY)

        Assert.assertEquals("Should be $EVENT_COUNT", EVENT_COUNT, tracker.getEventCount())

        // Track EVENT_COUNT events
        TestUtils.trackEventsOneByOne(tracker, EVENT_COUNT, TestUtils.EVENT_CATEGORY)

        val totalEventCount = EVENT_COUNT * 2
        Assert.assertEquals("Should be $totalEventCount", totalEventCount, tracker.getEventCount())
    }

    @Test
    fun testBatch() = runBlocking {

        // Init tracker
        val tracker = DummyStreamingTracker(100)
            .also { it.startConsuming() }

        Assert.assertTrue("Should be empty", tracker.isEmpty())

        // Track EVENT_COUNT events
        TestUtils.trackEventsBatch(tracker, EVENT_COUNT, TestUtils.EVENT_CATEGORY)

        Assert.assertEquals("Should be $EVENT_COUNT", EVENT_COUNT, tracker.getEventCount())

        // Track EVENT_COUNT events
        TestUtils.trackEventsBatch(tracker, EVENT_COUNT, TestUtils.EVENT_CATEGORY)

        val totalEventCount = EVENT_COUNT * 2
        Assert.assertEquals("Should be $totalEventCount", totalEventCount, tracker.getEventCount())
    }

    companion object {
        private const val EVENT_COUNT = 10

        @JvmStatic
        @BeforeClass
        fun initLogger() {
            Logger.initialize()
            Logger.addChild(SystemOutLog(Severity.VERBOSE))
        }
    }
}