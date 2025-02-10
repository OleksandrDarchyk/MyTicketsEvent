package dk.easv.myticketsevent.BE;


public class Coordinator {
    private int id;
    private String username;
    private String password;

    public Coordinator(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
