package com.cyrillrx.tracker.event;

import com.cyrillrx.tracker.context.TrackerContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Cyril Leroux
 *         Created on 11/11/2015.
 */
public class TrackEvent {

    protected final long createdAt;

    protected TrackerContext context;
    protected String category;
    protected String name;
    protected String source;

    protected String id;
    protected String type;

    protected Map<String, String> customAttributes;

    TrackEvent() {
        createdAt = System.currentTimeMillis();
        customAttributes = new HashMap<>();
    }

    public TrackEvent setContext(TrackerContext context) {
        this.context = context;
        return this;
    }

    public long getCreatedAt() { return createdAt; }

    public String getCategory() { return category; }

    public String getName() { return name; }

    public String getSource() { return source; }

    public String getId() { return id; }

    public String getType() { return type; }

    public Map<String, String> getCustomAttributes() { return customAttributes; }

    public static class Builder extends EventBuilder<TrackEvent> {

        public Builder() { event = new TrackEvent(); }

        @Override
        public TrackEvent build() {

            if (event.category == null || event.category.isEmpty()) {
                throw new IllegalStateException("Category is mandatory");
            }

            return event;
        }
    }
}
