package com.posyandu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import com.toedter.calendar.JDateChooser;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;


public class DataPetugasFormPage extends JFrame {
    private JTextField idPetugasField, namaField, alamatField, noTeleponField;
    private JComboBox<String> jenisKelaminField, jabatanField;
    private JDateChooser tanggalLahirChooser;
    private DataPetugasPage dataPetugasPage;
    private JButton saveButton;

    public DataPetugasFormPage(DataPetugasPage dataPetugasPage) {
        this.dataPetugasPage = dataPetugasPage;

        setTitle("Formulir Data Petugas");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(255, 223, 186));
        add(panel);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(255, 239, 213));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;

        // Title
        JLabel formTitle = new JLabel("Formulir Data Petugas", JLabel.CENTER);
        formTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        formTitle.setForeground(new Color(34, 139, 34));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(formTitle, gbc);
        gbc.gridwidth = 1;

        // Form fields
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(createLabel("ID Petugas:"), gbc);
        gbc.gridx = 1;
        idPetugasField = new JTextField();
        formPanel.add(idPetugasField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(createLabel("Nama:"), gbc);
        gbc.gridx = 1;
        namaField = new JTextField();
        formPanel.add(namaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(createLabel("Tanggal Lahir:"), gbc);
        gbc.gridx = 1;
        tanggalLahirChooser = new JDateChooser();
        formPanel.add(tanggalLahirChooser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(createLabel("Jenis Kelamin:"), gbc);
        gbc.gridx = 1;
        jenisKelaminField = new JComboBox<>(new String[]{"Pria", "Wanita"});
        formPanel.add(jenisKelaminField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(createLabel("Alamat:"), gbc);
        gbc.gridx = 1;
        alamatField = new JTextField();
        formPanel.add(alamatField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(createLabel("No Telepon:"), gbc);
        gbc.gridx = 1;
        noTeleponField = new JTextField();
        formPanel.add(noTeleponField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(createLabel("Jabatan:"), gbc);
        gbc.gridx = 1;
        jabatanField = new JComboBox<>(new String[]{"Bidan", "Asisten Bidan"});
        formPanel.add(jabatanField, gbc);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(255, 223, 186));

        saveButton = new JButton("Simpan");
        saveButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        saveButton.setBackground(new Color(135, 206, 250));
        saveButton.setForeground(Color.WHITE);
        saveButton.setEnabled(false);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePetugasData();
            }
        });
        buttonPanel.add(saveButton);

        JButton clearButton = new JButton("Hapus");
        clearButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        clearButton.setBackground(new Color(255, 69, 0));
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        buttonPanel.add(clearButton);

        JButton backButton = new JButton("Kembali");
        backButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        backButton.setBackground(new Color(255, 165, 0));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DataPetugasPage();
                dispose();
            }
        });
        buttonPanel.add(backButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        addKeyListeners();
        setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        label.setForeground(new Color(34, 139, 34));
        return label;
    }

private void savePetugasData() {
    String idPetugas = idPetugasField.getText();
    String nama = namaField.getText();
    String tanggalLahir = ((JTextField) tanggalLahirChooser.getDateEditor().getUiComponent()).getText();
    String jenisKelamin = (String) jenisKelaminField.getSelectedItem();
    String alamat = alamatField.getText();
    String noTelepon = noTeleponField.getText();
    String jabatan = (String) jabatanField.getSelectedItem();

    String sql = "INSERT INTO data_petugas (idPetugas, nama, tanggalLahir, jenisKelamin, alamat, noTelepon, jabatan) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection connection = DatabaseConnection.connect();
         PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, idPetugas);
        statement.setString(2, nama);
        statement.setString(3, tanggalLahir);
        statement.setString(4, jenisKelamin);
        statement.setString(5, alamat);
        statement.setString(6, noTelepon);
        statement.setString(7, jabatan);
        statement.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }

    JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
    dataPetugasPage.addPetugasData(idPetugas, nama, tanggalLahir, jenisKelamin, alamat, noTelepon, jabatan);
    dispose();
}


    private void clearFields() {
        idPetugasField.setText("");
        namaField.setText("");
        tanggalLahirChooser.setDate(null);
        jenisKelaminField.setSelectedIndex(0);
        alamatField.setText("");
        noTeleponField.setText("");
        jabatanField.setSelectedIndex(0);
    }

    private void addKeyListeners() {
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    ((JComponent) e.getSource()).transferFocus();
                }
                checkFields();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                checkFields();
            }
        };

        idPetugasField.addKeyListener(keyAdapter);
        namaField.addKeyListener(keyAdapter);
        ((JTextField) tanggalLahirChooser.getDateEditor().getUiComponent()).addKeyListener(keyAdapter);
        jenisKelaminField.addKeyListener(keyAdapter);
        alamatField.addKeyListener(keyAdapter);
        noTeleponField.addKeyListener(keyAdapter);
        jabatanField.addKeyListener(keyAdapter);
    }

    private void checkFields() {
        boolean allFieldsFilled = !idPetugasField.getText().trim().isEmpty() && !namaField.getText().trim().isEmpty()
                && tanggalLahirChooser.getDate() != null && jenisKelaminField.getSelectedItem() != null
                && !alamatField.getText().trim().isEmpty() && !noTeleponField.getText().trim().isEmpty()
                && jabatanField.getSelectedItem() != null;
        saveButton.setEnabled(allFieldsFilled);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DataPetugasFormPage(new DataPetugasPage());
            }
        });
    }
}


