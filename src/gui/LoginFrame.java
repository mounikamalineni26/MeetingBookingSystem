package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import services.BookingService;
import services.UserUtil;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleCombo;
    private BookingService bookingService;

    public LoginFrame() {
        setTitle("Meeting Room Booking - Login");
        setSize(500, 300); // Compact and centered window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        bookingService = new BookingService();

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        JLabel passLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        JLabel roleLabel = new JLabel("Role:");
        roleCombo = new JComboBox<>(new String[]{"User", "Admin"});

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        // Row 1
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(userLabel, gbc);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        // Row 2
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(passLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Row 3
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(roleLabel, gbc);
        gbc.gridx = 1;
        panel.add(roleCombo, gbc);

        // Row 4 - Buttons
        JPanel btnPanel = new JPanel();
        btnPanel.add(loginButton);
        btnPanel.add(registerButton);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnPanel, gbc);

        add(panel);

        // Login logic
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String role = ((String) roleCombo.getSelectedItem()).toLowerCase();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password.");
                return;
            }

            if (role.equals("admin") && username.equals("admin") && password.equals("admin123")) {
                new AdminDashboard("admin", bookingService).setVisible(true);
                dispose();
            } else if (UserUtil.validateUser(username, password, role)) {
                new UserDashboard(username).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!");
            }
        });

        // Register navigation
        registerButton.addActionListener(e -> {
            new RegisterFrame().setVisible(true);
            dispose();
        });

        setVisible(true);
    }
}
