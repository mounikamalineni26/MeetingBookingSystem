package services;

import models.Booking;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private List<Booking> bookings = new ArrayList<>();
    private static final String FILE_NAME = "bookings.txt";

    public BookingService() {
        loadBookingsFromFile(); // Load bookings into memory
    }

    // Updated method with user parameter
    public boolean isSlotAvailable(String user, String room, String date, String timeSlot) {
        for (Booking b : bookings) {
            // Check if anyone has booked the same slot
            if (b.getRoomName().equalsIgnoreCase(room) &&
                b.getDate().equals(date) &&
                b.getTimeSlot().equalsIgnoreCase(timeSlot)) {
                return false; // Slot already taken
            }

            // Prevent same user booking multiple slots in same room+date
            if (b.getUserName().equalsIgnoreCase(user) &&
                b.getRoomName().equalsIgnoreCase(room) &&
                b.getDate().equals(date)) {
                return false; // Already booked on same room+date
            }
        }
        return true;
    }

    // Book room if slot is available
    public boolean bookRoom(String user, String room, String date, String timeSlot) {
        if (isSlotAvailable(user, room, date, timeSlot)) {
            Booking newBooking = new Booking(user, room, date, timeSlot);
            bookings.add(newBooking);
            saveBookingToFile(newBooking);
            return true;
        }
        return false;
    }

    // Cancel booking
    public boolean cancelBooking(String user, String room, String date, String timeSlot) {
        Booking match = null;

        // Normalize input
        String normalizedInput = timeSlot.trim().toLowerCase().replaceAll("\\s+", "").replaceAll("am|pm", "");

        for (Booking b : bookings) {
            String normalizedBookingTime = b.getTimeSlot().trim().toLowerCase().replaceAll("\\s+", "").replaceAll("am|pm", "");
            if (b.getUserName().equalsIgnoreCase(user) &&
                b.getRoomName().equalsIgnoreCase(room) &&
                b.getDate().equals(date) &&
                normalizedBookingTime.equals(normalizedInput)) {
                match = b;
                break;
            }
        }

        if (match != null) {
            bookings.remove(match);
            rewriteFile();
            return true;
        }

        return false;
    }

    // GUI display of bookings
    public void showUserBookingsGUI(Component parent, String userName, boolean isAdmin) {
        StringBuilder sb = new StringBuilder();
        boolean found = false;

        for (Booking b : bookings) {
            if (isAdmin || b.getUserName().equalsIgnoreCase(userName)) {
                sb.append("👤 ").append(b.getUserName())
                  .append(" | 🏢 ").append(b.getRoomName())
                  .append(" | 📅 ").append(b.getDate())
                  .append(" | ⏰ ").append(b.getTimeSlot())
                  .append("\n");
                found = true;
            }
        }

        if (!found) {
            sb.append("❌ No bookings found.");
        }

        JTextArea textArea = new JTextArea(sb.toString(), 15, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(parent, scrollPane, "📋 Bookings", JOptionPane.INFORMATION_MESSAGE);
    }

    // Save one booking to file
    private void saveBookingToFile(Booking booking) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(booking.getUserName() + "," + booking.getRoomName() + "," + booking.getDate() + "," + booking.getTimeSlot());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("[ERROR] Could not save booking to file.");
        }
    }

    // Rewrite all bookings to file
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

    // Load bookings from file
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
}
