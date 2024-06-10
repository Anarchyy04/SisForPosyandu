package com.posyandu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import com.toedter.calendar.JDateChooser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DataAnakFormPage extends JFrame {
    private JTextField namaField, nikField;
    private JComboBox<String> jenisKelaminField;
    private JTextField namaOrtuField, noTelponField, alamatField;
    private JTextField beratBadanField, tinggiBadanField, lingkarKepalaField;
    private JDateChooser tanggalLahirChooser;
    private DataAnakPage dataAnakPage;
    private JButton saveButton;

    public DataAnakFormPage(DataAnakPage dataAnakPage) {
        this.dataAnakPage = dataAnakPage;

        setTitle("Formulir Data Anak");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(15, 15)); // Set margin between content and container
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Set margin between content and container
        panel.setBackground(new Color(255, 223, 186));
        add(panel);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 15, 15)); // Set margin between fields and the container
        formPanel.setBackground(new Color(255, 239, 213));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Set margin between fields and the container

        // Title
        JLabel formTitle = new JLabel("Formulir Data Anak", JLabel.CENTER);
        formTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        formTitle.setForeground(new Color(34, 139, 34));
        formPanel.add(formTitle);
        formPanel.add(new JLabel(""));

        // Section I: Identitas Anak
        JLabel identitasAnakLabel = new JLabel("I. Identitas Anak");
        identitasAnakLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        identitasAnakLabel.setForeground(new Color(34, 139, 34));
        formPanel.add(identitasAnakLabel);
        formPanel.add(new JLabel(""));

        formPanel.add(createLabel("Nama:"));
        namaField = new JTextField();
        formPanel.add(namaField);

        formPanel.add(createLabel("NIK:"));
        nikField = new JTextField();
        formPanel.add(nikField);

        formPanel.add(createLabel("Tanggal Lahir:"));
        tanggalLahirChooser = new JDateChooser();
        formPanel.add(tanggalLahirChooser);

        formPanel.add(createLabel("Jenis Kelamin:"));
        jenisKelaminField = new JComboBox<>(new String[]{"Pria", "Wanita"});
        formPanel.add(jenisKelaminField);

        // Section II: Identitas Orang Tua
        JLabel identitasOrtuLabel = new JLabel("II. Identitas Orang Tua");
        identitasOrtuLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        identitasOrtuLabel.setForeground(new Color(34, 139, 34));
        formPanel.add(identitasOrtuLabel);
        formPanel.add(new JLabel(""));

        formPanel.add(createLabel("Nama Ortu:"));
        namaOrtuField = new JTextField();
        formPanel.add(namaOrtuField);

        formPanel.add(createLabel("No Telepon:"));
        noTelponField = new JTextField();
        formPanel.add(noTelponField);

        formPanel.add(createLabel("Alamat:"));
        alamatField = new JTextField();
        formPanel.add(alamatField);

        // Section III: Data Kesehatan Anak
        JLabel dataKesehatanAnakLabel = new JLabel("III. Data Kesehatan Anak");
        dataKesehatanAnakLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        dataKesehatanAnakLabel.setForeground(new Color(34, 139, 34));
        formPanel.add(dataKesehatanAnakLabel);
        formPanel.add(new JLabel(""));

        formPanel.add(createLabel("Berat Badan:"));
        beratBadanField = new JTextField();
        formPanel.add(beratBadanField);

        formPanel.add(createLabel("Tinggi Badan:"));
        tinggiBadanField = new JTextField();
        formPanel.add(tinggiBadanField);

        formPanel.add(createLabel("Lingkar Kepala:"));
        lingkarKepalaField = new JTextField();
        formPanel.add(lingkarKepalaField);

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
                saveChildData();
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
                new DataAnakPage();
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

private void saveChildData() {
    String nama = namaField.getText();
    String nik = nikField.getText();
    String tanggalLahir = ((JTextField) tanggalLahirChooser.getDateEditor().getUiComponent()).getText();
    String jenisKelamin = (String) jenisKelaminField.getSelectedItem();
    String namaOrtu = namaOrtuField.getText();
    String noTelpon = noTelponField.getText();
    String alamat = alamatField.getText();
    String beratBadan = beratBadanField.getText();
    String tinggiBadan = tinggiBadanField.getText();
    String lingkarKepala = lingkarKepalaField.getText();

    String sql = "INSERT INTO data_anak (nik, nama, tanggal_lahir, jenis_kelamin, nama_ortu, no_telpon, alamat, berat_badan, tinggi_badan, lingkar_kepala) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = DatabaseConnection.connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, nik);
        pstmt.setString(2, nama);
        pstmt.setString(3, tanggalLahir);
        pstmt.setString(4, jenisKelamin);
        pstmt.setString(5, namaOrtu);
        pstmt.setString(6, noTelpon);
        pstmt.setString(7, alamat);
        pstmt.setString(8, beratBadan);
        pstmt.setString(9, tinggiBadan);
        pstmt.setString(10, lingkarKepala);
        pstmt.executeUpdate();
        JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage());
    }

    new DataAnakPage();
    dispose();
}


    private void clearFields() {
        namaField.setText("");
        nikField.setText("");
        tanggalLahirChooser.setDate(null);
        jenisKelaminField.setSelectedIndex(0);
        namaOrtuField.setText("");
        noTelponField.setText("");
        alamatField.setText("");
        beratBadanField.setText("");
        tinggiBadanField.setText("");
        lingkarKepalaField.setText("");
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

        namaField.addKeyListener(keyAdapter);
        nikField.addKeyListener(keyAdapter);
        ((JTextField) tanggalLahirChooser.getDateEditor().getUiComponent()).addKeyListener(keyAdapter);
        jenisKelaminField.addKeyListener(keyAdapter);
        namaOrtuField.addKeyListener(keyAdapter);
        noTelponField.addKeyListener(keyAdapter);
        alamatField.addKeyListener(keyAdapter);
        beratBadanField.addKeyListener(keyAdapter);
        tinggiBadanField.addKeyListener(keyAdapter);
        lingkarKepalaField.addKeyListener(keyAdapter);
    }

    private void checkFields() {
        boolean allFieldsFilled = !namaField.getText().trim().isEmpty() && !nikField.getText().trim().isEmpty()
                && tanggalLahirChooser.getDate() != null && jenisKelaminField.getSelectedItem() != null
                && !namaOrtuField.getText().trim().isEmpty() && !noTelponField.getText().trim().isEmpty()
                && !alamatField.getText().trim().isEmpty() && !beratBadanField.getText().trim().isEmpty()
                && !tinggiBadanField.getText().trim().isEmpty() && !lingkarKepalaField.getText().trim().isEmpty();
        saveButton.setEnabled(allFieldsFilled);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DataAnakFormPage(new DataAnakPage());
            }
        });
    }
}

class DataAnakPage {
    public void addChildData(String nama, String nik, String tanggalLahir, String jenisKelamin, String namaOrtu, String noTelpon, String alamat, String beratBadan, String tinggiBadan, String lingkarKepala) {
        // Logic to add child data
    }

    public static void main(String[] args) {
        // Placeholder main method to avoid compilation errors
    }
}







