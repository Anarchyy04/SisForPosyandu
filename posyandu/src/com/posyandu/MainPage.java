package com.posyandu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame {
    public MainPage() {
        // Set up the frame
        setTitle("Posyandu Ceria");
        setSize(1020, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(255, 223, 186)); // Lighter and more cheerful background color
        add(panel);

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);

        // Load and scale the logo image
        ImageIcon originalLogoIcon = new ImageIcon("C:\\Users\\rian yuliawan\\OneDrive\\Dokumen\\posyandu\\src\\com\\posyandu\\asset\\LogoPosyandu.png");
        Image originalLogoImage = originalLogoIcon.getImage();
        Image scaledLogoImage = originalLogoImage.getScaledInstance(60, 60, Image.SCALE_SMOOTH); // Increased size
        ImageIcon scaledLogoIcon = new ImageIcon(scaledLogoImage);

        JLabel logoLabel = new JLabel(scaledLogoIcon);
        JLabel titleLabel = new JLabel("Posyandu Ceria");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 36)); // More cheerful and larger font
        titleLabel.setForeground(new Color(34, 139, 34));

        headerPanel.add(logoLabel);
        headerPanel.add(titleLabel);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 40, 40)); // Adjusted to 2x2 grid
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.setBackground(new Color(255, 223, 186)); // Same background color
        panel.add(buttonPanel, BorderLayout.CENTER);

        // Buttons without icons
        JButton btnDataAnak = createButton("Data Anak", new Color(135, 206, 250));
        JButton btnDataPenimbangan = createButton("Data Penimbangan", new Color(30, 144, 255));
        JButton btnDataImunisasi = createButton("Data Imunisasi", new Color(255, 140, 0));
        JButton btnDataPetugas = createButton("Data Petugas", new Color(60, 179, 113));

        // Add buttons to the panel in the specified order
        buttonPanel.add(btnDataAnak);
        buttonPanel.add(btnDataPenimbangan);
        buttonPanel.add(btnDataImunisasi);
        buttonPanel.add(btnDataPetugas);

        // Make the frame visible
        setVisible(true);
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 24)); // Larger and more cheerful font
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 200)); // Larger size

        // Set button with rounded corners and shadow effect
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Removed white border
        button.setUI(new RoundedButtonUI());

        // Adding hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
                button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Change cursor back to default
            }
        });

        button.addActionListener(new ButtonClickListener());

        return button;
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            switch (source.getText()) {
                case "Data Imunisasi":
                    new DataImunisasiPage();
                    break;
                case "Data Anak":
                    new DataAnakPage();
                    break;
                case "Data Penimbangan":
                    new DataPenimbanganPage();
                    break;
                case "Data Petugas":
                    new DataPetugasPage();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "You clicked " + source.getText(), "Info", JOptionPane.INFORMATION_MESSAGE);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        new MainPage();
    }
}

class RoundedButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        paintBackground(g, b, b.getModel().isPressed() ? 2 : 0);
        super.paint(g, c);
    }

    private void paintBackground(Graphics g, JComponent c, int yOffset) {
        Dimension size = c.getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(c.getBackground().darker());
        g2.fillRoundRect(0, yOffset, size.width, size.height - yOffset, 30, 30);
        g2.setColor(c.getBackground());
        g2.fillRoundRect(0, yOffset, size.width, size.height + yOffset - 2, 30, 30);
        // Adding a thicker shadow effect
        g2.setColor(new Color(0, 0, 0, 50)); // Shadow color
        g2.fillRoundRect(5, 5, size.width, size.height, 30, 30);
    }
}



