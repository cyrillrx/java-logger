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

    public static class Builder {

        private final TrackEvent event;

        public Builder() { event = new TrackEvent(); }

        public TrackEvent build() {

            if (event.category == null || event.category.isEmpty()) {
                throw new IllegalStateException("Category is mandatory");
            }

            return event;
        }

        public Builder setContext(TrackerContext context) {
            event.context = context;
            return this;
        }

        public Builder setCategory(String category) {
            event.category = category;
            return this;
        }

        public Builder setName(String name) {
            event.name = name;
            return this;
        }

        public Builder setSource(String source) {
            event.source = source;
            return this;
        }

        public Builder setId(String id) {
            event.id = id;
            return this;
        }

        public Builder setType(String type) {
            event.type = type;
            return this;
        }

        public Builder putCustomAttribute(String key, String value) {
            event.customAttributes.put(key, value);
            return this;
        }

        public Builder putCustomAttributes(Map<String, String> values) {
            event.customAttributes.putAll(values);
            return this;
        }
    }
}
