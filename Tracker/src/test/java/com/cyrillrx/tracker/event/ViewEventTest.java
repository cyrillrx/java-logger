package com.cyrillrx.tracker.event;

import com.cyrillrx.tracker.TestUtils;
import org.junit.Test;

/**
 * @author Cyril Leroux
 *         Created on 27/04/2016.
 */
public class ViewEventTest {

    @Test
    public void testBuild() {

        final ViewEvent event = new ViewEvent.Builder()
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
}
