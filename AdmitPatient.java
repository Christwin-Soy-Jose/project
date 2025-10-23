// src/hospital/AdmitPatient.java
package hospital;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdmitPatient extends JFrame {
    private JTextField nameField, ageField, genderField, phoneField, addressField;

    public AdmitPatient() {
        setTitle("Admit New Patient");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 5, 5));
        getContentPane().setBackground(new Color(0, 153, 153));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Age:"));
        ageField = new JTextField();
        add(ageField);

        add(new JLabel("Gender:"));
        genderField = new JTextField();
        add(genderField);

        add(new JLabel("Phone:"));
        phoneField = new JTextField();
        add(phoneField);

        add(new JLabel("Address:"));
        addressField = new JTextField();
        add(addressField);

        JButton saveBtn = new JButton("Save");
        saveBtn.setBackground(Color.GREEN);
        saveBtn.addActionListener(e -> savePatient());
        add(saveBtn);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBackground(Color.RED);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.addActionListener(e -> dispose());
        add(cancelBtn);

        setVisible(true);
    }

    private void savePatient() {
        String name = nameField.getText();
        String ageText = ageField.getText();
        String gender = genderField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        if (name.isEmpty() || ageText.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name, Age, and Phone are required!");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Age must be a number!");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO patients (name, age, gender, phone, address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, gender);
            stmt.setString(4, phone);
            stmt.setString(5, address);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Patient admitted successfully!");
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving patient!");
        }
    }
}