package com.cyrillrx.tracker.event;

import com.cyrillrx.tracker.context.TrackerContext;

import java.util.Map;

/**
 * @author Cyril Leroux
 *         Created on 24/11/2015.
 */
public class ActionEvent extends TrackEvent {

    protected String action;

    protected int intValue;
    protected long longValue;
    protected float floatValue;
    protected boolean boolValue;

    protected ActionEvent() { }

    public String getAction() { return action; }

    public int getIntValue() { return intValue; }

    public long getLongValue() { return longValue; }

    public float getFloatValue() { return floatValue; }

    public boolean getBoolValue() { return boolValue; }

    public static class Builder extends EventBuilder<ActionEvent> {

        public Builder() { event = new ActionEvent(); }

        @Override
        public ActionEvent build() {

            if (event.category == null || event.category.isEmpty() ||
                    event.action == null || event.action.isEmpty()) {
                throw new IllegalStateException("Category and action are mandatory");
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

        public Builder setAction(String action) {
            event.action = action;
            return this;
        }

        public Builder setValue(int value) {
            event.intValue = value;
            return this;
        }

        public Builder setValue(long value) {
            event.longValue = value;
            return this;
        }

        public Builder setValue(float value) {
            event.floatValue = value;
            return this;
        }

        public Builder setValue(boolean value) {
            event.boolValue = value;
            return this;
        }
    }
}