package com.cyrillrx.tracker.event;

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

    ActionEvent() { }

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