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
    public void testBuild() {

        final TrackEvent event = new TrackEvent.Builder()
                .setContext(TestUtils.createFakeContext())
                .setCategory(TestUtils.EVENT_CATEGORY)
                .setName(TestUtils.EVENT_NAME)
                .setSource(TestUtils.EVENT_SOURCE)
                .setId(TestUtils.EVENT_ID)
                .setType(TestUtils.EVENT_TYPE)
                .putCustomAttribute(TestUtils.KEY_1, TestUtils.VALUE_1)
                .putCustomAttribute(TestUtils.KEY_2, TestUtils.VALUE_2)
                .build();

        TestUtils.assertTrackEventConsistency(event);
    }

    @Test
    public void testBuildWithoutCategory() {

        try {
            TrackEvent event = new TrackEvent.Builder()
                    .build();
            Assert.fail("Should have fail (no category filled).");

        } catch (IllegalStateException ignored) { }
    }

    @Test
    public void testBuildWithEmptyCategory() {

        try {
            TrackEvent event = new TrackEvent.Builder()
                    .setCategory("")
                    .build();
            Assert.fail("Should have fail (no category filled).");

        } catch (IllegalStateException ignored) { }
    }
}
