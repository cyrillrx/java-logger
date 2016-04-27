package com.cyrillrx.tracker.event;

import com.cyrillrx.tracker.context.TrackerContext;

import java.util.Map;

/**
 * @author Cyril Leroux
 *         Created on 25/04/2016.
 */
public abstract class EventBuilder<Event extends TrackEvent> {

    protected Event event;

    public abstract Event build();

    public EventBuilder setContext(TrackerContext context) {
        event.context = context;
        return this;
    }

    public EventBuilder setCategory(String category) {
        event.category = category;
        return this;
    }

    public EventBuilder setName(String name) {
        event.name = name;
        return this;
    }

    public EventBuilder setSource(String source) {
        event.source = source;
        return this;
    }

    public EventBuilder putCustomAttribute(String key, String value) {
        event.customAttributes.put(key, value);
        return this;
    }

    public EventBuilder putCustomAttributes(Map<String, String> values) {
        event.customAttributes.putAll(values);
        return this;
    }

    @Deprecated
    public EventBuilder setId(String id) {
        event.id = id;
        return this;
    }

    @Deprecated
    public EventBuilder setType(String type) {
        event.type = type;
        return this;
    }
}
