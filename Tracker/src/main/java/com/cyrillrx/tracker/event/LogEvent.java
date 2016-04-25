package com.cyrillrx.tracker.event;

/**
 * @author Cyril Leroux
 *         Created on 25/04/2016.
 */
public class LogEvent extends TrackEvent {

    protected int severity;
    protected int priority;
    protected String message;
    protected String screen;
    protected String screenExecId;

    LogEvent() { }

    public int getSeverity() { return severity; }

    public int getPriority() { return priority; }

    public String getMessage() { return message; }

    public String getScreen() { return screen; }

    public String getScreenExecId() { return screenExecId; }

    public static class Builder extends EventBuilder<LogEvent> {

        public Builder() { event = new LogEvent(); }

        public LogEvent build() {

            if (event.category == null || event.category.isEmpty()) {
                throw new IllegalStateException("Category is mandatory");
            }

            return event;
        }

        public Builder setSeverity(int severity) {
            event.severity = severity;
            return this;
        }

        public Builder setPriority(int priority) {
            event.priority = priority;
            return this;
        }

        public Builder setMessage(String message) {
            event.message = message;
            return this;
        }

        public Builder setScreen(String screen) {
            event.screen = screen;
            return this;
        }

        public Builder setScreenExecId(String screenExecId) {
            event.screenExecId = screenExecId;
            return this;
        }
    }
}
