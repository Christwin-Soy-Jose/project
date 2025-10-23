// src/hospital/Welcome.java
package hospital;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Welcome extends JFrame {
    private String userRole;

    public Welcome(String role) {
        this.userRole = role;
        setTitle("Welcome - " + role.toUpperCase());
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(0, 153, 153));

        JLabel label = new JLabel("Welcome to Maya's Clinic, " + role + "!", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        add(label, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setOpaque(false);

        if (role.equals("admin")) {
            JButton docBtn = new JButton("Manage Doctors");
            docBtn.addActionListener(e -> new DoctorModule().setVisible(true));
            btnPanel.add(docBtn);
        }

        JButton patBtn = new JButton("Manage Patients");
        patBtn.addActionListener(e -> new PatientModule().setVisible(true));
        btnPanel.add(patBtn);

        JButton logout = new JButton("Logout");
        logout.setBackground(Color.RED);
        logout.setForeground(Color.WHITE);
        logout.addActionListener(e -> {
            new LoginPage().setVisible(true);
            dispose();
        });
        btnPanel.add(logout);

        add(btnPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}