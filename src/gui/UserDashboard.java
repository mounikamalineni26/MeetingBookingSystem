package gui;

import services.BookingService;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserDashboard extends JFrame {
    private final BookingService bookingService;

    public UserDashboard(String username) {
        this.bookingService = new BookingService();

        setTitle("User Dashboard - " + username);
        setSize(420, 360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        JButton bookRoomBtn = new JButton("📝 Book a Room");
        JButton viewBookingsBtn = new JButton("📋 View My Bookings");
        JButton cancelBookingBtn = new JButton("❌ Cancel a Booking");
        JButton logoutBtn = new JButton("🔒 Logout");

        Dimension btnSize = new Dimension(250, 40);
        for (JButton btn : new JButton[]{bookRoomBtn, viewBookingsBtn, cancelBookingBtn, logoutBtn}) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(btnSize);
            buttonPanel.add(btn);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        add(buttonPanel, BorderLayout.CENTER);

        // Book Room
        bookRoomBtn.addActionListener(e -> new BookingFrame(username));

        // View My Bookings
        viewBookingsBtn.addActionListener(e -> bookingService.showUserBookingsGUI(this, username, false));

        // Cancel Booking (Improved UI)
        cancelBookingBtn.addActionListener(e -> {
            JPanel cancelPanel = new JPanel(new GridLayout(3, 2, 10, 10));
            JTextField roomField = new JTextField();

            // Date Picker
            SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH);
            JSpinner dateSpinner = new JSpinner(dateModel);
            JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
            dateSpinner.setEditor(editor);

            // Time Slot Dropdown
            String[] timeSlots = {
                "09:00-10:00", "10:00-11:00", "11:00-12:00",
                "01:00-02:00", "02:00-03:00", "03:00-04:00"
            };
            JComboBox<String> timeSlotBox = new JComboBox<>(timeSlots);

            cancelPanel.add(new JLabel("🏢 Room Name:"));
            cancelPanel.add(roomField);
            cancelPanel.add(new JLabel("📆 Date:"));
            cancelPanel.add(dateSpinner);
            cancelPanel.add(new JLabel("⏰ Time Slot:"));
            cancelPanel.add(timeSlotBox);

            int result = JOptionPane.showConfirmDialog(this, cancelPanel, "❌ Cancel Booking",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String room = roomField.getText().trim();
                String date = new SimpleDateFormat("yyyy-MM-dd").format((Date) dateSpinner.getValue());
                String time = (String) timeSlotBox.getSelectedItem();

                if (room.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "❗ Room name is required.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean success = bookingService.cancelBooking(username, room, date, time);
                if (success) {
                    JOptionPane.showMessageDialog(this, "✅ Booking cancelled successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ No matching booking found.");
                }
            }
        });

        // Logout
        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?",
                    "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });

        setVisible(true);
    }
}
