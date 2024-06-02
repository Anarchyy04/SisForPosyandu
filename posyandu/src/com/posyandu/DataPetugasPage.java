package com.posyandu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataPetugasPage extends JFrame {
    public DataPetugasPage() {
        // Set up the frame
        setTitle("Data Petugas");
        setSize(1020, 560);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(255, 223, 186)); // Lighter and more cheerful background color
        add(panel);

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Data Petugas");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 36)); // More cheerful and larger font
        titleLabel.setForeground(new Color(34, 139, 34));

        headerPanel.add(titleLabel);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(255, 223, 186)); // Same background color
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        panel.add(contentPanel, BorderLayout.CENTER);

        // Add some example data
        JLabel exampleData = new JLabel("Data Petugas Example");
        exampleData.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
        exampleData.setForeground(new Color(34, 139, 34));
        contentPanel.add(exampleData);

        // Back Button
        JButton backButton = new JButton("Back to Main Page");
        backButton.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        backButton.setBackground(new Color(60, 179, 113));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainPage();
                dispose();
            }
        });
        panel.add(backButton, BorderLayout.SOUTH);

        // Make the frame visible
        setVisible(true);
    }
}
