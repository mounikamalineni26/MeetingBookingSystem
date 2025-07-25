package gui;

import services.BookingService;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard(String adminUsername, BookingService bookingService) {
        setTitle("Admin Dashboard - " + adminUsername);
        setSize(420, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JLabel title = new JLabel("Welcome, Admin!", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        JButton viewAllBtn = new JButton("📋 View All Bookings");
        JButton cancelBookingBtn = new JButton("❌ Cancel Any Booking");
        JButton logoutBtn = new JButton("🔒 Logout");

        Dimension btnSize = new Dimension(250, 40);
        for (JButton btn : new JButton[]{viewAllBtn, cancelBookingBtn, logoutBtn}) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(btnSize);
            buttonPanel.add(btn);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        add(buttonPanel, BorderLayout.CENTER);

        // View All Bookings (GUI popup)
        viewAllBtn.addActionListener(e -> {
            bookingService.showUserBookingsGUI(this, "", true);
        });

        // Cancel Booking Flow
        cancelBookingBtn.addActionListener(e -> {
            JPanel cancelPanel = new JPanel(new GridLayout(4, 2, 10, 10));
            JTextField userField = new JTextField();
            JTextField roomField = new JTextField();
            JTextField dateField = new JTextField();
            JTextField timeField = new JTextField();

            cancelPanel.add(new JLabel("Username:"));
            cancelPanel.add(userField);
            cancelPanel.add(new JLabel("Room Name:"));
            cancelPanel.add(roomField);
            cancelPanel.add(new JLabel("Date (YYYY-MM-DD):"));
            cancelPanel.add(dateField);
            cancelPanel.add(new JLabel("Time Slot (e.g., 10:00-11:00):"));
            cancelPanel.add(timeField);

            int result = JOptionPane.showConfirmDialog(this, cancelPanel, "Cancel Booking",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String user = userField.getText().trim();
                String room = roomField.getText().trim();
                String date = dateField.getText().trim();
                String timeSlot = timeField.getText().trim();

                if (user.isEmpty() || room.isEmpty() || date.isEmpty() || timeSlot.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "❗ All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean success = bookingService.cancelBooking(user, room, date, timeSlot);
                if (success) {
                    JOptionPane.showMessageDialog(this, "✅ Booking cancelled.");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ No matching booking found.");
                }
            }
        });

        // Logout with confirmation
        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });

        setVisible(true);
    }
}
