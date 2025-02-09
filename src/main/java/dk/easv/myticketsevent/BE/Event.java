package dk.easv.myticketsevent.BE;


public class Event {
    private String name;
    private String location;
    private String dateTime;

    public Event(String name, String location, String dateTime) {
        this.name = name;
        this.location = location;
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDateTime() {
        return dateTime;
    }
}
