// src/hospital/EditDoctor.java
package hospital;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class EditDoctor extends JFrame {
    private JTextField nameField, specField, phoneField;
    private JComboBox<Integer> doctorCombo;
    private JButton loadBtn, updateBtn, deleteBtn, cancelBtn;

    public EditDoctor() {
        setTitle("Edit Doctor Details");
        setSize(500, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(0, 153, 153));
        setLayout(new BorderLayout());

        // Top panel for doctor selection
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setOpaque(false);
        topPanel.add(new JLabel("Select Doctor:"));
        doctorCombo = new JComboBox<>();
        loadDoctors();
        topPanel.add(doctorCombo);
        loadBtn = new JButton("Load");
        loadBtn.setBackground(Color.BLUE);
        loadBtn.setForeground(Color.WHITE);
        loadBtn.addActionListener(e -> loadDoctorDetails());
        topPanel.add(loadBtn);
        add(topPanel, BorderLayout.NORTH);

        // Center panel for doctor details
        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        centerPanel.setOpaque(false);

        centerPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        centerPanel.add(nameField);

        centerPanel.add(new JLabel("Specialization:"));
        specField = new JTextField();
        centerPanel.add(specField);

        centerPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        centerPanel.add(phoneField);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom panel for buttons
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setOpaque(false);

        updateBtn = new JButton("Update");
        updateBtn.setBackground(Color.GREEN);
        updateBtn.addActionListener(e -> updateDoctor());
        bottomPanel.add(updateBtn);

        deleteBtn = new JButton("Delete");
        deleteBtn.setBackground(Color.RED);
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.addActionListener(e -> deleteDoctor());
        bottomPanel.add(deleteBtn);

        cancelBtn = new JButton("Cancel");
        cancelBtn.setBackground(Color.ORANGE);
        cancelBtn.addActionListener(e -> dispose());
        bottomPanel.add(cancelBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadDoctors() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT doc_id, name FROM doctors ORDER BY name";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            doctorCombo.removeAllItems();
            while (rs.next()) {
                doctorCombo.addItem(rs.getInt("doc_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading doctors!");
        }
    }

    private void loadDoctorDetails() {
        if (doctorCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a doctor!");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM doctors WHERE doc_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, (Integer) doctorCombo.getSelectedItem());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nameField.setText(rs.getString("name"));
                specField.setText(rs.getString("specialization"));
                phoneField.setText(rs.getString("phone"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading doctor details!");
        }
    }

    private void updateDoctor() {
        if (doctorCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a doctor!");
            return;
        }

        String name = nameField.getText();
        String spec = specField.getText();
        String phone = phoneField.getText();

        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and Phone are required!");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE doctors SET name=?, specialization=?, phone=? WHERE doc_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, spec);
            stmt.setString(3, phone);
            stmt.setInt(4, (Integer) doctorCombo.getSelectedItem());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Doctor updated successfully!");
            loadDoctors();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating doctor!");
        }
    }

    private void deleteDoctor() {
        if (doctorCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a doctor!");
            return;
        }

        int result = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this doctor?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            try (Connection conn = DBConnection.getConnection()) {
                if (conn == null) {
                    JOptionPane.showMessageDialog(this, "Database connection failed. Check DB settings.");
                    return;
                }
                String sql = "DELETE FROM doctors WHERE doc_id=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, (Integer) doctorCombo.getSelectedItem());
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Doctor deleted successfully!");
                    loadDoctors();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "No doctor was deleted. It may not exist.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting doctor!");
            }
        }
    }

    private void clearFields() {
        nameField.setText("");
        specField.setText("");
        phoneField.setText("");
    }
}
