package com.posyandu;

import javax.swing.*;
import java.awt.*;

public class DataAnakFormPage extends JFrame {
    private DataAnakPage dataAnakPage;

    public DataAnakFormPage(DataAnakPage dataAnakPage) {
        this.dataAnakPage = dataAnakPage;

        // Set up the frame
        setTitle("Tambah Data Anak");
        setSize(1020, 560);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 223, 186));
        add(mainPanel);

        // Create scrollable panel for form fields
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(255, 223, 186));

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add form fields
        JTextField namaField = addField(formPanel, "Nama:");
        JTextField nikField = addField(formPanel, "NIK:");
        JTextField tanggalLahirField = addField(formPanel, "Tanggal Lahir:");
        JTextField jenisKelaminField = addField(formPanel, "Jenis Kelamin:");
        JTextField namaOrtuField = addField(formPanel, "Nama Orang Tua:");
        JTextField noTelponField = addField(formPanel, "No Telpon:");
        JTextField alamatField = addField(formPanel, "Alamat Lengkap:");
        JTextField beratBadanField = addField(formPanel, "Berat Badan:");
        JTextField tinggiBadanField = addField(formPanel, "Tinggi Badan:");
        JTextField lingkarKepalaField = addField(formPanel, "Lingkar Kepala:");
        JTextField riwayatImunisasiField = addField(formPanel, "Riwayat Imunisasi:");
        JTextField riwayatPenyakitField = addField(formPanel, "Riwayat Penyakit:");

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(255, 223, 186));

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        submitButton.setBackground(new Color(135, 206, 250));
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(e -> {
            // Add data to table
            dataAnakPage.addChildData(
                namaField.getText(),
                nikField.getText(),
                tanggalLahirField.getText(),
                jenisKelaminField.getText(),
                namaOrtuField.getText(),
                noTelponField.getText(),
                alamatField.getText(),
                beratBadanField.getText(),
                tinggiBadanField.getText(),
                lingkarKepalaField.getText(),
                riwayatImunisasiField.getText(),
                riwayatPenyakitField.getText()
            );
            JOptionPane.showMessageDialog(this, "Data anak berhasil ditambahkan!");
            new DataAnakPage();
            dispose();
        });
        buttonPanel.add(submitButton);

        // Delete button
        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        deleteButton.setBackground(new Color(135, 206, 250));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(e -> {
            // Clear all input fields
            namaField.setText("");
            nikField.setText("");
            tanggalLahirField.setText("");
            jenisKelaminField.setText("");
            namaOrtuField.setText("");
            noTelponField.setText("");
            alamatField.setText("");
            beratBadanField.setText("");
            tinggiBadanField.setText("");
            lingkarKepalaField.setText("");
            riwayatImunisasiField.setText("");
            riwayatPenyakitField.setText("");
        });
        buttonPanel.add(deleteButton);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        backButton.setBackground(new Color(135, 206, 250));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> {
            new DataAnakPage();
            dispose();
        });
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Make the frame visible
        setVisible(true);
    }

    private JTextField addField(JPanel panel, String label) {
        panel.add(new JLabel(label));
        JTextField textField = new JTextField();
        panel.add(textField);
        return textField;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DataAnakFormPage(new DataAnakPage()));
    }
}



