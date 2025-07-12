package models;
public class Booking {
    private String userName;
    private String roomName;
    private String date;
    private String timeSlot;

    public Booking(String userName, String roomName, String date, String timeSlot) {
        this.userName = userName;
        this.roomName = roomName;
        this.date = date;
        this.timeSlot = timeSlot;
    }

    public String getUserName() {
        return userName;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getDate() {
        return date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    @Override
    public String toString() {
        return roomName + " booked by " + userName + " on " + date + " at " + timeSlot;
    }
}
