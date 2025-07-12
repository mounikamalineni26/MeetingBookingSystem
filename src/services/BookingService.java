package services;

import models.Booking;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private List<Booking> bookings = new ArrayList<>();
    private static final String FILE_NAME = "bookings.txt";

    public BookingService() {
        loadBookingsFromFile(); // Load bookings on startup
    }

    public boolean isSlotAvailable(String room, String date, String timeSlot) {
        for (Booking b : bookings) {
            if (b.getRoomName().equalsIgnoreCase(room) &&
                b.getDate().equals(date) &&
                b.getTimeSlot().equals(timeSlot)) {
                return false;
            }
        }
        return true;
    }

    public boolean bookRoom(String user, String room, String date, String timeSlot) {
        if (isSlotAvailable(room, date, timeSlot)) {
            Booking newBooking = new Booking(user, room, date, timeSlot);
            bookings.add(newBooking);
            saveBookingToFile(newBooking);
            return true;
        }
        return false;
    }

    public boolean cancelBooking(String user, String room, String date, String timeSlot) {
        Booking match = null;
        for (Booking b : bookings) {
            if (b.getUserName().equalsIgnoreCase(user) &&
                b.getRoomName().equalsIgnoreCase(room) &&
                b.getDate().equals(date) &&
                b.getTimeSlot().equals(timeSlot)) {
                match = b;
                break;
            }
        }

        if (match != null) {
            bookings.remove(match);
            rewriteFile(); // Update file
            return true;
        }

        return false;
    }

    public void showUserBookings(String userName, boolean isAdmin) {
        boolean found = false;
        for (Booking b : bookings) {
            if (isAdmin || b.getUserName().equalsIgnoreCase(userName)) {
                System.out.println(b);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No bookings found.");
        }
    }

    // ------------------- FILE METHODS -------------------

    private void saveBookingToFile(Booking booking) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(booking.getUserName() + "," + booking.getRoomName() + "," + booking.getDate() + "," + booking.getTimeSlot());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("[ERROR] Could not save booking to file.");
        }
    }

    private void loadBookingsFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("[INFO] No existing bookings found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split(",");
                if (parts.length == 4) {
                    bookings.add(new Booking(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Could not load bookings from file.");
        }
    }

    private void rewriteFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Booking b : bookings) {
                bw.write(b.getUserName() + "," + b.getRoomName() + "," + b.getDate() + "," + b.getTimeSlot());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Could not rewrite bookings file.");
        }
    }
}
