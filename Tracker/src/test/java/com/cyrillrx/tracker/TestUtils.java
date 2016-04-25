package com.cyrillrx.tracker;

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

    static void wait100Millis() {
        Utils.wait(100, TimeUnit.MILLISECONDS);
    }
}
