package com.cyrillrx.tracker;

import com.cyrillrx.tracker.context.TrackerContext;
import com.cyrillrx.tracker.event.TrackEvent;
import com.cyrillrx.tracker.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Cyril Leroux
 *         Created on 25/04/16.
 */
public class TestUtils {

    public static final String EVENT_CATEGORY = "ScreenView";
    public static final String EVENT_NAME = "Home screen view";
    public static final String EVENT_SOURCE = "AppClassOrScreen";
    public static final String EVENT_ID = "home";
    public static final String EVENT_TYPE = "screen";

    public static final String KEY_CUSTOM_1 = "key_custom_1";
    public static final String KEY_CUSTOM_2 = "key_custom_2";

    public static TrackerContext createFakeContext() {
        return new TrackerContext()
                .setApp(new TrackerContext.App("AwesomeApp", "2.0"))
                .setUser(new TrackerContext.User()
                        .setId("john_doe")
                        .setName("John Doe")
                        .setEmail("john_doe@company.com"))
                .setDevice(new TrackerContext.Device());
    }

    static TrackEvent createFakeEvent(String category) {
        return new TrackEvent.Builder().setCategory(category).build();
    }

    static void trackEventsOneByOne(TrackerChild tracker, int count, String category) {
        for (int i = 0; i < count; i++) {
            tracker.track(createFakeEvent(category));
        }
    }

    static void trackEventsBatch(TrackerChild tracker, int count, String category) {

        final List<TrackEvent> eventBucket = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            eventBucket.add(createFakeEvent(category));
        }
        tracker.track(eventBucket);
    }

    static void wait100Millis() { Utils.wait(100, TimeUnit.MILLISECONDS); }
}
