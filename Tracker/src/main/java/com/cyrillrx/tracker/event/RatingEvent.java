package com.cyrillrx.tracker.event;

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

        public Builder setRating(int rating) {
            isRated = true;
            event.rating = rating;
            return this;
        }
    }
}