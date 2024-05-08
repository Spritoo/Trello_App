package messagingSystem;

import java.io.Serializable;

public class Event implements Serializable {
    private String eventName;
    private String eventData;

    // Constructors
    public Event() {
        // Default constructor
    }

    public Event(String eventName, String eventData) {
        this.eventName = eventName;
        this.eventData = eventData;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventData() {
        return eventData;
    }

    public void setEventData(String eventData) {
        this.eventData = eventData;
    }

    // Override toString for debugging purposes
    @Override
    public String toString() {
        return "Event { " +
                ", eventName='" + eventName + '\'' +
                ", eventData='" + eventData + '\'' +
                " }";
    }
}
