package com.cyrillrx.tracker.event;

import com.cyrillrx.tracker.TestUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Cyril Leroux
 *         Created on 27/04/2016.
 */
public class ActionEventTest {

    @Test
    public void testBuild() {

        final ActionEvent event = new ActionEvent.Builder()
                .setContext(TestUtils.createFakeContext())
                .setCategory(TestUtils.EVENT_CATEGORY)
                .setName(TestUtils.EVENT_NAME)
                .setSource(TestUtils.EVENT_SOURCE)
                .setId(TestUtils.EVENT_ID)
                .setType(TestUtils.EVENT_TYPE)
                .putCustomAttribute(TestUtils.KEY_1, TestUtils.VALUE_1)
                .putCustomAttribute(TestUtils.KEY_2, TestUtils.VALUE_2)
                .setAction("Action")
                .setValue(10)
                .setValue(5.5F)
                .setValue(42L)
                .setValue(true)
                .build();

        TestUtils.assertTrackEventConsistency(event);

        Assert.assertEquals("Event action is inconsistent.", "Action", event.getAction());
        Assert.assertEquals("Event int value is inconsistent.", 10, event.getIntValue());
        Assert.assertEquals("Event float value is inconsistent.", 5.5F, event.getFloatValue(), 5.5F - event.getFloatValue());
        Assert.assertEquals("Event action is inconsistent.", 42L, event.getLongValue());
        Assert.assertEquals("Event action is inconsistent.", true, event.getBoolValue());
    }
}
