package com.cyrillrx.tracker.event;

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
    }
}