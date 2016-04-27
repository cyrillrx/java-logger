package com.cyrillrx.tracker.event;

import com.cyrillrx.tracker.context.TrackerContext;

import java.util.Map;

/**
 * @author Cyril Leroux
 *         Created on 26/11/2015.
 */
public class ViewEvent extends TrackEvent {

    ViewEvent() { }

    public static class Builder extends EventBuilder<ViewEvent> {

        public Builder() { event = new ViewEvent(); }

        @Override
        public ViewEvent build() {

            if (event.category == null || event.category.isEmpty() ||
                    event.name == null || event.name.isEmpty()) {
                throw new IllegalStateException("Category and name are mandatory");
            }

            return event;
        }

        @Override
        public Builder setContext(TrackerContext context) { return (Builder) super.setContext(context); }

        @Override
        public Builder setCategory(String category) { return (Builder) super.setCategory(category); }

        @Override
        public Builder setName(String name) { return (Builder) super.setName(name); }

        @Override
        public Builder setSource(String source) { return (Builder) super.setSource(source); }

        @Override
        public Builder putCustomAttribute(String key, String value) {
            return (Builder) super.putCustomAttribute(key, value);
        }

        @Override
        public Builder putCustomAttributes(Map<String, String> values) {
            return (Builder) super.putCustomAttributes(values);
        }

        @Deprecated
        @Override
        public Builder setId(String id) { return (Builder) super.setId(id); }

        @Deprecated
        @Override
        public Builder setType(String type) { return (Builder) super.setType(type); }
    }
}