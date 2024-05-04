//package messagingSystem;
//
//import java.io.Serializable;
//
//public class Event implements Serializable {
//    private String eventId;
//    private String eventName;
//    private String eventDescription;
//    private long eventTimestamp;
//
//    // Constructors
//    public Event() {
//        // Default constructor
//    }
//
//    public Event(String eventId, String eventName, String eventDescription, long eventTimestamp) {
//        this.eventId = eventId;
//        this.eventName = eventName;
//        this.eventDescription = eventDescription;
//        this.eventTimestamp = eventTimestamp;
//    }
//
//    // Getters and Setters
//    public String getEventId() {
//        return eventId;
//    }
//
//    public void setEventId(String eventId) {
//        this.eventId = eventId;
//    }
//
//    public String getEventName() {
//        return eventName;
//    }
//
//    public void setEventName(String eventName) {
//        this.eventName = eventName;
//    }
//
//    public String getEventDescription() {
//        return eventDescription;
//    }
//
//    public void setEventDescription(String eventDescription) {
//        this.eventDescription = eventDescription;
//    }
//
//    public long getEventTimestamp() {
//        return eventTimestamp;
//    }
//
//    public void setEventTimestamp(long eventTimestamp) {
//        this.eventTimestamp = eventTimestamp;
//    }
//
//    // Override toString for debugging purposes
//    @Override
//    public String toString() {
//        return "Event{" +
//                "eventId='" + eventId + '\'' +
//                ", eventName='" + eventName + '\'' +
//                ", eventDescription='" + eventDescription + '\'' +
//                ", eventTimestamp=" + eventTimestamp +
//                '}';
//    }
//}
