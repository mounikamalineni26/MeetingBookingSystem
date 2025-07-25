package gui;

import services.BookingService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookingFrame extends JFrame {
    private final JTextField roomField;
    private final JSpinner dateSpinner;
    private final JComboBox<String> timeSlotCombo;
    private final String username;
    private final BookingService bookingService;

    public BookingFrame(String username) {
        this.username = username;
        this.bookingService = new BookingService();

        setTitle("📅 Book Meeting Room");
        setSize(420, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Confirm before closing
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                        BookingFrame.this,
                        "Are you sure you want to logout?",
                        "Logout Confirmation",
                        JOptionPane.YES_NO_OPTION
                );
                if (choice == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 🏢 Room Name
        JLabel roomLabel = new JLabel("🏢 Room Name:");
        roomField = new JTextField(20);

        // 📆 Date Picker
        JLabel dateLabel = new JLabel("📆 Date:");
        dateSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(editor);

        // ⏰ Time Slot Dropdown
        JLabel timeLabel = new JLabel("⏰ Time Slot:");
        timeSlotCombo = new JComboBox<>(new String[]{
                "09:00-10:00", "10:00-11:00", "11:00-12:00",
                "12:00-01:00", "01:00-02:00", "02:00-03:00",
                "03:00-04:00", "04:00-05:00"
        });

        // Add Components
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(roomLabel, gbc);
        gbc.gridx = 1;
        panel.add(roomField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(dateLabel, gbc);
        gbc.gridx = 1;
        panel.add(dateSpinner, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(timeLabel, gbc);
        gbc.gridx = 1;
        panel.add(timeSlotCombo, gbc);

        // ✅ Book Now Button
        JButton bookBtn = new JButton("✅ Book Now");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(bookBtn, gbc);

        bookBtn.addActionListener(e -> bookRoom());

        add(panel);
        setVisible(true);
    }

    private void bookRoom() {
        String room = roomField.getText().trim();
        String date = new SimpleDateFormat("yyyy-MM-dd").format((Date) dateSpinner.getValue());
        String time = (String) timeSlotCombo.getSelectedItem();

        if (room.isEmpty()) {
            JOptionPane.showMessageDialog(this, "❗ Room name is required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = bookingService.bookRoom(username, room, date, time);
        if (success) {
            JOptionPane.showMessageDialog(this, "✅ Booking successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "⚠️ Slot already booked!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
