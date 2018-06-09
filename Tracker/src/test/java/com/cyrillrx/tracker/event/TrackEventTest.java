package com.cyrillrx.tracker.event;

import com.cyrillrx.tracker.TestUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Cyril Leroux
 *         Created on 25/04/2016.
 */
public class TrackEventTest {

    @Test
    public void testBuildEvent() {

        final TrackEvent event = new TrackEvent.Builder()
                .setContext(TestUtils.createFakeContext())
                .setCategory(TestUtils.EVENT_CATEGORY)
                .setName(TestUtils.EVENT_NAME)
                .setSource(TestUtils.EVENT_SOURCE)
                .putCustomAttribute(TestUtils.KEY_1, TestUtils.VALUE_1)
                .putCustomAttribute(TestUtils.KEY_2, TestUtils.VALUE_2)
                .build();

        TestUtils.assertTrackEventConsistency(event);
    }

    @Test
    public void testBuildRatingEvent() {

        final TrackEvent event = new TrackEvent.Builder()
                .setContext(TestUtils.createFakeContext())
                .setCategory(TestUtils.EVENT_CATEGORY)
                .setName(TestUtils.EVENT_NAME)
                .setSource(TestUtils.EVENT_SOURCE)
                .putCustomAttribute(TestUtils.KEY_1, TestUtils.VALUE_1)
                .putCustomAttribute(TestUtils.KEY_2, TestUtils.VALUE_2)
                .putCustomAttribute("string", "toto")
                .putCustomAttribute("float", Float.MAX_VALUE)
                .putCustomAttribute("long", Long.MAX_VALUE)
                .putCustomAttribute("int", Integer.MAX_VALUE)
                .build();

        TestUtils.assertTrackEventConsistency(event);

        final String string = event.getCustomAttribute("string");
        Assert.assertEquals("Attribute is inconsistent.", "toto", string);

        final float floatValue = event.getCustomAttribute("float");
        Assert.assertEquals("Attribute is inconsistent.", Float.MAX_VALUE, floatValue);

        final long longValue = event.getCustomAttribute("long");
        Assert.assertEquals("Event rating is inconsistent.", Long.MAX_VALUE, longValue);

        final int intValue = event.getCustomAttribute("int");
        Assert.assertEquals("Event rating is inconsistent.", Integer.MAX_VALUE, intValue);
    }

    @Test
    public void testBuildWithoutCategory() {

        try {
            new TrackEvent.Builder().build();
            Assert.fail("Should have fail (no category filled).");

        } catch (IllegalStateException ignored) { }
    }

    @Test
    public void testBuildWithEmptyCategory() {

        try {
            new TrackEvent.Builder().setCategory("").build();
            Assert.fail("Should have fail (no category filled).");

        } catch (IllegalStateException ignored) { }
    }
}
