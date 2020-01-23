package com.cyrillrx.tracker.event

import com.cyrillrx.tracker.TestUtils
import org.junit.Assert
import org.junit.Test

/**
 * @author Cyril Leroux
 *         Created on 25/04/2016.
 */
class TrackEventTest {

    @Test
    fun testBuildEvent() {

        val flatMap: Map<String, String> = mapOf("flat_1" to "flat_1")
        val complexMap: Map<String, Any> = mapOf("complex_1" to StringWrapper("complex_1"))

        val event = TrackEvent.Builder()
            .setCategory(TestUtils.EVENT_CATEGORY)
            .setName(TestUtils.EVENT_NAME)
            .setSource(TestUtils.EVENT_SOURCE)
            .putCustomAttribute(TestUtils.KEY_1, TestUtils.VALUE_1)
            .putCustomAttribute(TestUtils.KEY_2, TestUtils.VALUE_2)
            .putCustomAttributes(flatMap)
            .putCustomAttributes(complexMap)
            .build()

        TestUtils.assertTrackEventConsistency(event)

        val flat1 = event.getCustomAttribute<String>("flat_1")
        Assert.assertEquals("Attribute is inconsistent.", "flat_1", flat1)

        val complex1: StringWrapper? = event.getCustomAttribute("complex_1")
        Assert.assertEquals("Attribute is inconsistent.", "complex_1", complex1?.value)
    }

    @Test
    fun buildRatingEvent() {

        val event = TrackEvent.Builder()
            .setCategory(TestUtils.EVENT_CATEGORY)
            .setName(TestUtils.EVENT_NAME)
            .setSource(TestUtils.EVENT_SOURCE)
            .putCustomAttribute(TestUtils.KEY_1, TestUtils.VALUE_1)
            .putCustomAttribute(TestUtils.KEY_2, TestUtils.VALUE_2)
            .putCustomAttribute("string", "toto")
            .putCustomAttribute("float", Float.MAX_VALUE)
            .putCustomAttribute("long", Long.MAX_VALUE)
            .putCustomAttribute("int", Int.MAX_VALUE)
            .build()

        TestUtils.assertTrackEventConsistency(event)

        val string = event.getCustomAttribute<String>("string")
        Assert.assertEquals("Attribute is inconsistent.", "toto", string)

        val floatValue = event.getCustomAttribute<Float>("float") ?: Float.MIN_VALUE
        Assert.assertEquals("Attribute is inconsistent.", Float.MAX_VALUE, floatValue, 0.1f)

        val longValue: Long = event.getCustomAttribute<Long>("long") ?: Long.MIN_VALUE
        Assert.assertEquals("Event rating is inconsistent.", Long.MAX_VALUE, longValue)

        val intValue = event.getCustomAttribute<Int>("int") ?: Long.MIN_VALUE
        Assert.assertEquals("Event rating is inconsistent.", Int.MAX_VALUE, intValue)
    }

    @Test
    fun testPrepareEventBeforeSending() {

        val event = TrackEvent.Builder()
            .setCategory(TestUtils.EVENT_CATEGORY)
            .setName(TestUtils.EVENT_NAME)
            .build()

        event.context = TestUtils.createFakeContext()
        event.updateSentAt()

        Assert.assertTrue(event.isValid())
    }

    @Test
    fun testPrepareEventWithoutUpdatingSentAt() {

        val event = TrackEvent.Builder()
            .setCategory(TestUtils.EVENT_CATEGORY)
            .setName(TestUtils.EVENT_NAME)
            .build()

        event.context = TestUtils.createFakeContext()

        Assert.assertFalse(event.isValid())
    }

    @Test
    fun testPrepareEventWithoutSettingContext() {

        val event = TrackEvent.Builder()
            .setCategory(TestUtils.EVENT_CATEGORY)
            .setName(TestUtils.EVENT_NAME)
            .build()

        event.updateSentAt()

        Assert.assertFalse(event.isValid())
    }

    @Test
    fun testBuildEventWithoutCategory() {
        try {
            TrackEvent.Builder()
                .setName(TestUtils.EVENT_NAME)
                .build()

            Assert.fail("Should have fail (no category filled).")
        } catch (ignored: Exception) {
            Assert.assertTrue(ignored is IllegalArgumentException)
        }
    }

    @Test
    fun testBuildEventWithEmptyCategory() {
        try {
            TrackEvent.Builder()
                .setCategory("")
                .setName(TestUtils.EVENT_NAME)
                .build()

            Assert.fail("Should have fail (no name filled).")
        } catch (ignored: Exception) {
            Assert.assertTrue(ignored is IllegalArgumentException)
        }
    }

    @Test
    fun testBuildEventWithBlankCategory() {
        try {
            TrackEvent.Builder()
                .setCategory("    ")
                .setName(TestUtils.EVENT_NAME)
                .build()

            Assert.fail("Should have fail (no name filled).")
        } catch (ignored: Exception) {
            Assert.assertTrue(ignored is IllegalArgumentException)
        }
    }

    @Test
    fun testBuildEventWithoutName() {
        try {
            TrackEvent.Builder()
                .setCategory(TestUtils.EVENT_CATEGORY)
                .build()
            Assert.fail("Should have fail (no name filled).")
        } catch (ignored: Exception) {
            Assert.assertTrue(ignored is IllegalArgumentException)
        }
    }

    @Test
    fun testBuildEventWithEmptyName() {
        try {
            TrackEvent.Builder()
                .setCategory(TestUtils.EVENT_CATEGORY)
                .setName("")
                .build()
            Assert.fail("Should have fail (no category filled).")
        } catch (ignored: Exception) {
            Assert.assertTrue(ignored is IllegalArgumentException)
        }
    }

    @Test
    fun testBuildEventWithBlankName() {
        try {
            TrackEvent.Builder()
                .setCategory(TestUtils.EVENT_CATEGORY)
                .setName("    ")
                .build()
            Assert.fail("Should have fail (no category filled).")
        } catch (ignored: Exception) {
            Assert.assertTrue(ignored is IllegalArgumentException)
        }
    }

    private class StringWrapper(val value: String)
}