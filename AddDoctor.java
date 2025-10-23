// src/hospital/AddDoctor.java
package hospital;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddDoctor extends JFrame {
    private JTextField name, spec, phone;

    public AddDoctor() {
        setTitle("Add New Doctor");
        setSize(400, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));
        getContentPane().setBackground(new Color(0, 153, 153));

        add(new JLabel("Name:"));
        name = new JTextField();
        add(name);

        add(new JLabel("Specialization:"));
        spec = new JTextField();
        add(spec);

        add(new JLabel("Phone:"));
        phone = new JTextField();
        add(phone);

        JButton save = new JButton("Save");
        save.setBackground(Color.GREEN);
        save.addActionListener(e -> saveDoctor());
        add(save);

        JButton cancel = new JButton("Cancel");
        cancel.setBackground(Color.RED);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(e -> dispose());
        add(cancel);

        setVisible(true);
    }

    private void saveDoctor() {
        String n = name.getText();
        String s = spec.getText();
        String p = phone.getText();

        if (n.isEmpty() || p.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and Phone are required!");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO doctors (name, specialization, phone) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, n);
            stmt.setString(2, s);
            stmt.setString(3, p);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Doctor added successfully!");
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving doctor!");
        }
    }
}