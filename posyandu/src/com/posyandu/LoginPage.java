package com.posyandu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton showPasswordButton;
    private boolean isPasswordVisible = false;

    public LoginPage() {
        // Set up the frame
        setTitle("Posyandu Ceria - Login");
        setSize(1020, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a panel with a background image
        JPanel panel = new BackgroundPanel();
        panel.setLayout(null);
        add(panel);

        // Title Label
        JLabel titleLabel = new JLabel("Posyandu Ceria");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        titleLabel.setForeground(new Color(255, 105, 180));  // Vibrant pink color for visibility
        titleLabel.setBounds(380, 50, 300, 50);
        panel.add(titleLabel);

        // Username Label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        usernameLabel.setForeground(new Color(0, 191, 255));  // Deep sky blue color for visibility
        usernameLabel.setBounds(310, 150, 150, 30);
        panel.add(usernameLabel);

        // Username Field
        usernameField = new JTextField();
        usernameField.setBounds(460, 150, 250, 30);
        panel.add(usernameField);

        // Add key listener to username field
        usernameField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    passwordField.requestFocus();
                }
            }
        });

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        passwordLabel.setForeground(new Color(50, 205, 50));  // Lime green color for visibility
        passwordLabel.setBounds(310, 220, 150, 30);
        panel.add(passwordLabel);

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setBounds(460, 220, 250, 30);
        panel.add(passwordField);

        // Show Password Button
        showPasswordButton = new JButton("Show");
        showPasswordButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        showPasswordButton.setBackground(new Color(255, 182, 193));  // Light pink color
        showPasswordButton.setForeground(Color.BLACK);
        showPasswordButton.setBounds(720, 220, 80, 30);
        panel.add(showPasswordButton);

        // Add action listener to show password button
        showPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPasswordVisible) {
                    passwordField.setEchoChar('*');
                    showPasswordButton.setText("Show");
                } else {
                    passwordField.setEchoChar((char) 0);
                    showPasswordButton.setText("Hide");
                }
                isPasswordVisible = !isPasswordVisible;
            }
        });

        // Add key listener to password field
        passwordField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick();
                }
            }
        });

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        loginButton.setBackground(new Color(144, 238, 144));
        loginButton.setForeground(new Color(0, 128, 0));
        loginButton.setBounds(460, 290, 120, 40);
        panel.add(loginButton);

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticate();
            }
        });

        // Footer Label
        JLabel footerLabel = new JLabel("Sistem Informasi Posyandu Kel. Rempoa");
        footerLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        footerLabel.setForeground(new Color(255, 223, 0));  // Gold color for visibility
        footerLabel.setBounds(370, 480, 300, 30);
        panel.add(footerLabel);

        // Make the frame visible
        setVisible(true);
    }

    private void authenticate() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();

        if ("Admin".equals(username) && "Hallo*Admin123".equals(new String(password))) {
            dispose(); // Close the login page
            new MainPage(); // Open the main page
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Inner class to handle background image
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                backgroundImage = new ImageIcon("C:\\Users\\rian yuliawan\\OneDrive\\Dokumen\\posyandu\\src\\com\\posyandu\\asset\\BackgroundLoginPage.png").getImage(); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                g.setColor(new Color(0, 0, 0, 100)); // Black color with transparency
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}




