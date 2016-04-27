package com.cyrillrx.tracker.event;

import com.cyrillrx.tracker.context.TrackerContext;

import java.util.Map;

/**
 * @author Cyril Leroux
 *         Created on 11/11/2015.
 */
public class RatingEvent extends TrackEvent {

    protected int rating;

    RatingEvent() { }

    public int getRating() { return rating; }

    public static class Builder extends EventBuilder<RatingEvent> {

        protected boolean isRated;

        public Builder() {
            event = new RatingEvent();
            isRated = false;
        }

        @Override
        public RatingEvent build() {

            if (event.category == null || event.category.isEmpty() ||
                    event.id == null || event.id.isEmpty() ||
                    event.type == null || event.type.isEmpty() ||
                    !isRated) {
                throw new IllegalStateException("Category, id, type and rating are mandatory");
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

        @Override
        public Builder setId(String id) { return (Builder) super.setId(id); }

        @Override
        public Builder setType(String type) { return (Builder) super.setType(type); }

        public Builder setRating(int rating) {
            isRated = true;
            event.rating = rating;
            return this;
        }
    }
}