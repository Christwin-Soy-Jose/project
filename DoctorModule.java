// src/hospital/DoctorModule.java
package hospital;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DoctorModule extends JFrame {
    public DoctorModule() {
        setTitle("Doctor Management");
        setSize(500, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(0, 153, 153));

        setLayout(new GridLayout(4, 1, 10, 10));

        JButton add = new JButton("Add Doctor");
        add.addActionListener(e -> new AddDoctor().setVisible(true));
        add(add);

        JButton edit = new JButton("Edit Doctor");
        edit.setBackground(Color.CYAN);
        edit.addActionListener(e -> new EditDoctor().setVisible(true));
        add(edit);

        JButton view = new JButton("View Doctors");
        view.addActionListener(e -> new ViewDoctors().setVisible(true));
        add(view);

        JButton back = new JButton("Back");
        back.addActionListener(e -> dispose());
        add(back);

        setVisible(true);
    }
}