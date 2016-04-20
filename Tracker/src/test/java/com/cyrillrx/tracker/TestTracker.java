package com.cyrillrx.tracker;

/**
 * @author Cyril Leroux
 *         Created on 20/04/16.
 */
public class TestTracker extends RealTimeTracker {

    public TestTracker(TrackerChild tracker, int capacity, int workerCount, TrackFilter filter) {
        super(tracker, capacity, workerCount, filter);
    }

    public TestTracker(TrackerChild tracker, int capacity, int workerCount) {
        super(tracker, capacity, workerCount);
    }

    public TestTracker(TrackerChild tracker, int capacity) {
        super(tracker, capacity);
    }
}
