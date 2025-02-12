package dk.easv.myticketsevent.BE;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Ticket {
    private UUID id;
    private String eventName;
    private String location;
    private LocalDate date;
    private LocalTime time;
    private String participantName;

    public Ticket(UUID id, String eventName, String location, LocalDate date, LocalTime time, String participantName) {
        this.id = id;
        this.eventName = eventName;
        this.location = location;
        this.date = date;
        this.time = time;
        this.participantName = participantName;
    }

    public UUID getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public String getLocation() {
        return location;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getParticipantName() {
        return participantName;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", location='" + location + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", participantName='" + participantName + '\'' +
                '}';
    }
}
