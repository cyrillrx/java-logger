package com.cyrillrx.tracker

import com.cyrillrx.tracker.context.TrackerContext
import com.cyrillrx.tracker.context.TrackerContext.App
import com.cyrillrx.tracker.context.TrackerContext.Device
import com.cyrillrx.tracker.event.TrackEvent
import com.cyrillrx.tracker.utils.waitFor
import kotlinx.coroutines.delay
import org.junit.Assert
import java.util.ArrayList
import java.util.concurrent.TimeUnit
import kotlin.random.Random

/**
 * @author Cyril Leroux
 * Created on 25/04/2016.
 */
object TestUtils {

    const val EVENT_CATEGORY = "ScreenView"
    const val EVENT_NAME = "Home screen view"
    const val EVENT_SOURCE = "AppClassOrScreen"

    const val KEY_1 = "key_custom_1"
    const val KEY_2 = "key_custom_2"

    const val VALUE_1 = "hello"
    const val VALUE_2 = "world"

    fun createFakeContext(): TrackerContext {
        return TrackerContext()
            .setApp(App("AwesomeApp", "2.0"))
            .setUser(
                TrackerContext.User()
                    .setId("john_doe")
                    .setName("John Doe")
                    .setEmail("john_doe@company.com")
            )
            .setDevice(Device())
    }

    fun createFakeEvent(category: String): TrackEvent =
        TrackEvent.Builder()
            .setCategory(category)
            .setName(Random(10).nextInt(10).toString())
            .build()

    suspend fun trackEventsOneByOne(tracker: TrackerChild, count: Int, category: String) {
        for (i in 0 until count) {
            tracker.track(createFakeEvent(category))
        }
        delay(10L)
    }

    suspend fun trackEventsBatch(tracker: TrackerChild, count: Int, category: String) {
        val eventBucket: MutableList<TrackEvent> = ArrayList()
        for (i in 0 until count) {
            eventBucket.add(createFakeEvent(category))
        }
        tracker.track(eventBucket)
        delay(10L)
    }

    fun wait100Millis() {
        waitFor(100, TimeUnit.MILLISECONDS)
    }

    fun assertTrackEventConsistency(event: TrackEvent) {
        Assert.assertEquals("Event category is inconsistent.", EVENT_CATEGORY, event.category)
        Assert.assertEquals("Event name is inconsistent.", EVENT_NAME, event.name)
        Assert.assertEquals("Event source is inconsistent.", EVENT_SOURCE, event.source)

        Assert.assertEquals("Custom attr 1 is inconsistent.", VALUE_1, event.getCustomAttribute(KEY_1))
        Assert.assertEquals("Custom attr 2 is inconsistent.", VALUE_2, event.getCustomAttribute(KEY_2))
    }
}