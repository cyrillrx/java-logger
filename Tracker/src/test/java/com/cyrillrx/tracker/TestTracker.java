package com.cyrillrx.tracker;

import com.cyrillrx.tracker.event.TrackEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Cyril Leroux
 *         Created on 22/04/2016.
 */
class TestTracker implements TrackerChild {

    final List<String> categories = new ArrayList<>();

    @Override
    public void track(TrackEvent event) {

        categories.add(event.getCategory());
        System.out.println(event.getCategory());
    }

    @Override
    public void track(Collection<TrackEvent> events) {
        for (TrackEvent event : events) {
            track(event);
        }
    }

    List<String> getCategories() { return categories; }

    int getCount() { return categories.size(); }

}
