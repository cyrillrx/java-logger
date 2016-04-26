package com.cyrillrx.tracker.event;

import com.cyrillrx.tracker.TestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

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
                .putCustomAttribute(TestUtils.KEY_CUSTOM_1, "hello")
                .putCustomAttribute(TestUtils.KEY_CUSTOM_2, "world")
                .build();

        Assert.assertEquals("Event category is inconsistent.", TestUtils.EVENT_CATEGORY, event.getCategory());
        Assert.assertEquals("Event name is inconsistent.", TestUtils.EVENT_NAME, event.getName());
        Assert.assertEquals("Event source is inconsistent.", TestUtils.EVENT_SOURCE, event.getSource());
        Assert.assertEquals("Event id is inconsistent.", TestUtils.EVENT_ID, event.getId());
        Assert.assertEquals("Event type is inconsistent.", TestUtils.EVENT_TYPE, event.getType());

        final Map<String, String> customAttributes = event.getCustomAttributes();
        Assert.assertEquals("Custom attributes count is inconsistent.", 2, customAttributes.size());
        Assert.assertEquals("Custom attr 1 is inconsistent.", "hello", customAttributes.get(TestUtils.KEY_CUSTOM_1));
        Assert.assertEquals("Custom attr 2 is inconsistent.", "world", customAttributes.get(TestUtils.KEY_CUSTOM_2));
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
