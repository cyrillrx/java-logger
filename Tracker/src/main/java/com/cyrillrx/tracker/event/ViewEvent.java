package com.cyrillrx.tracker.event;

import com.cyrillrx.tracker.context.TrackerContext;

import java.util.Map;

/**
 * @author Cyril Leroux
 *         Created on 26/11/2015.
 */
public class ViewEvent extends TrackEvent {

    ViewEvent() { }

    public static class Builder {

        private final ViewEvent event;

        public Builder() { event = new ViewEvent(); }

        public ViewEvent build() {

            if (event.category == null || event.category.isEmpty() ||
                    event.name == null || event.name.isEmpty()) {
                throw new IllegalStateException("Category and name are mandatory");
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