package com.posyandu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import com.toedter.calendar.JDateChooser;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class DataImunisasiFormPage extends JFrame {
    private JTextField nikField, namaField, tanggalLahirField, idImunisasiField, catatanField, idPetugasField, namaPetugasField;
    private JDateChooser tanggalImunisasiChooser;
    private JComboBox<String> jenisImunisasiComboBox;
    private DataImunisasiPage dataImunisasiPage;
    private JButton saveButton, searchNamaButton, searchIdPetugasButton;

    public DataImunisasiFormPage(DataImunisasiPage dataImunisasiPage) {
        this.dataImunisasiPage = dataImunisasiPage;

        setTitle("Formulir Data Imunisasi");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(255, 223, 186));
        add(panel);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(255, 239, 213));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title
        JLabel formTitle = new JLabel("Formulir Data Imunisasi", JLabel.CENTER);
        formTitle.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        formTitle.setForeground(new Color(34, 139, 34));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(formTitle, gbc);
        gbc.gridwidth = 1;

        // Section I: Data Anak
        JLabel dataAnakLabel = new JLabel("I. Data Anak");
        dataAnakLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        dataAnakLabel.setForeground(new Color(34, 139, 34));
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(dataAnakLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(new JLabel(""), gbc);

        addLabeledField(formPanel, "NIK:", gbc, 2, nikField = new JTextField(20), searchNamaButton = new JButton("Cari"));
        addLabeledField(formPanel, "Nama:", gbc, 4, namaField = new JTextField(20));
        addLabeledField(formPanel, "Tanggal Lahir:", gbc, 6, tanggalLahirField = new JTextField(20));

        // Set namaField and tanggalLahirField as non-editable
        namaField.setEditable(false);
        tanggalLahirField.setEditable(false);

        // Section II: Data Imunisasi
        JLabel dataImunisasiLabel = new JLabel("II. Data Imunisasi");
        dataImunisasiLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        dataImunisasiLabel.setForeground(new Color(34, 139, 34));
        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(dataImunisasiLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(new JLabel(""), gbc);

        gbc.gridwidth = 2;
        addLabeledComponent(formPanel, "Tanggal Imunisasi:", gbc, 9, tanggalImunisasiChooser = new JDateChooser());
        gbc.gridwidth = 1;

        addLabeledField(formPanel, "ID Imunisasi:", gbc, 11, idImunisasiField = new JTextField(20));
        addLabeledComponent(formPanel, "Jenis Imunisasi:", gbc, 13, jenisImunisasiComboBox = new JComboBox<>(new String[]{"BCG", "Polio", "DPT", "Hepatitis B", "Campak", "HiB"}));
        addLabeledField(formPanel, "Catatan:", gbc, 15, catatanField = new JTextField(20));

        // Section III: Data Petugas
        JLabel dataPetugasLabel = new JLabel("III. Data Petugas");
        dataPetugasLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        dataPetugasLabel.setForeground(new Color(34, 139, 34));
        gbc.gridx = 0;
        gbc.gridy = 17;
        formPanel.add(dataPetugasLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(new JLabel(""), gbc);

        addLabeledField(formPanel, "ID Petugas:", gbc, 18, idPetugasField = new JTextField(20), searchIdPetugasButton = new JButton("Cari"));
        addLabeledField(formPanel, "Nama Petugas:", gbc, 20, namaPetugasField = new JTextField(20));

        // Set namaPetugasField as non-editable
        namaPetugasField.setEditable(false);

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
                saveData();
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
                dispose();
            }
        });
        buttonPanel.add(backButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        searchNamaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchNama();
            }
        });

        searchIdPetugasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPetugas();
            }
        });

        addKeyListeners();
        setVisible(true);
    }

    private void addLabeledField(JPanel panel, String labelText, GridBagConstraints gbc, int yPos, JTextField textField) {
        addLabeledField(panel, labelText, gbc, yPos, textField, null);
    }

    private void addLabeledField(JPanel panel, String labelText, GridBagConstraints gbc, int yPos, JTextField textField, JButton button) {
        gbc.gridx = 0;
        gbc.gridy = yPos;
        panel.add(createLabel(labelText), gbc);
        gbc.gridx = 1;
        gbc.gridy = yPos;
        if (button != null) {
            JPanel panelWithButton = new JPanel(new BorderLayout());
            panelWithButton.add(textField, BorderLayout.CENTER);
            panelWithButton.add(button, BorderLayout.EAST);
            panel.add(panelWithButton, gbc);
        } else {
            panel.add(textField, gbc);
        }
    }

    private void addLabeledComponent(JPanel panel, String labelText, GridBagConstraints gbc, int yPos, JComponent component) {
        gbc.gridx = 0;
        gbc.gridy = yPos;
        panel.add(createLabel(labelText), gbc);
        gbc.gridx = 1;
        gbc.gridy = yPos;
        panel.add(component, gbc);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        label.setForeground(new Color(34, 139, 34));
        return label;
    }

    private void saveData() {
        String nik = nikField.getText();
        String nama = namaField.getText();
        String tanggalLahir = tanggalLahirField.getText();
        String tanggalImunisasi = ((JTextField) tanggalImunisasiChooser.getDateEditor().getUiComponent()).getText();
        String idImunisasi = idImunisasiField.getText();
        String jenisImunisasi = (String) jenisImunisasiComboBox.getSelectedItem();
        String catatan = catatanField.getText();
        String idPetugas = idPetugasField.getText();
        String namaPetugas = namaPetugasField.getText();

        dataImunisasiPage.addImunisasiData(nik, nama, tanggalLahir, tanggalImunisasi, idImunisasi, jenisImunisasi, catatan, idPetugas, namaPetugas);
        saveToDatabase(nik, nama, tanggalLahir, idImunisasi, tanggalImunisasi, jenisImunisasi, catatan, idPetugas, namaPetugas);
        JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
        new DataImunisasiPage();
        dispose();
    }

    private void saveToDatabase(String nik, String nama, String tanggalLahir, String idImunisasi, String tanggalImunisasi, String jenisImunisasi, String catatan, String idPetugas, String namaPetugas) {
        String url = "jdbc:sqlite:C:\\Users\\rian yuliawan\\OneDrive\\Dokumen\\posyandu\\src\\com\\posyandu\\asset\\posyandu.db";
        String sql = "INSERT INTO data_imunisasi(nik, nama, tanggal_lahir, id_imunisasi, tanggal_imunisasi, jenis_imunisasi, catatan, id_petugas, nama_petugas) VALUES(?,?,?,?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nik);
            pstmt.setString(2, nama);
            pstmt.setString(3, tanggalLahir);
            pstmt.setString(4, idImunisasi);
            pstmt.setString(5, tanggalImunisasi);
            pstmt.setString(6, jenisImunisasi);
            pstmt.setString(7, catatan);
            pstmt.setString(8, idPetugas);
            pstmt.setString(9, namaPetugas);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage());
        }
    }

    private void clearFields() {
        nikField.setText("");
        namaField.setText("");
        tanggalLahirField.setText("");
        tanggalImunisasiChooser.setDate(null);
        idImunisasiField.setText("");
        jenisImunisasiComboBox.setSelectedIndex(0);
        catatanField.setText("");
        idPetugasField.setText("");
        namaPetugasField.setText("");
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

        nikField.addKeyListener(keyAdapter);
        namaField.addKeyListener(keyAdapter);
        tanggalLahirField.addKeyListener(keyAdapter);
        ((JTextField) tanggalImunisasiChooser.getDateEditor().getUiComponent()).addKeyListener(keyAdapter);
        idImunisasiField.addKeyListener(keyAdapter);
        catatanField.addKeyListener(keyAdapter);
        idPetugasField.addKeyListener(keyAdapter);
        namaPetugasField.addKeyListener(keyAdapter);
        jenisImunisasiComboBox.addKeyListener(keyAdapter);
    }

    private void checkFields() {
        boolean allFieldsFilled = !nikField.getText().trim().isEmpty() && !namaField.getText().trim().isEmpty()
                && !tanggalLahirField.getText().trim().isEmpty() && tanggalImunisasiChooser.getDate() != null
                && !idImunisasiField.getText().trim().isEmpty() && jenisImunisasiComboBox.getSelectedItem() != null
                && !catatanField.getText().trim().isEmpty() && !idPetugasField.getText().trim().isEmpty()
                && !namaPetugasField.getText().trim().isEmpty();
        saveButton.setEnabled(allFieldsFilled);
    }

    private void searchNama() {
        String nik = nikField.getText().trim();
        if (!nik.isEmpty()) {
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\rian yuliawan\\OneDrive\\Dokumen\\posyandu\\src\\com\\posyandu\\asset\\posyandu.db")) {
                String sql = "SELECT nama, tanggal_lahir FROM data_anak WHERE nik = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, nik);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        namaField.setText(rs.getString("nama"));
                        tanggalLahirField.setText(rs.getString("tanggal_lahir"));
                    } else {
                        JOptionPane.showMessageDialog(this, "NIK tidak ditemukan");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error fetching data: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "NIK tidak boleh kosong");
        }
    }

    private void searchPetugas() {
        String idPetugas = idPetugasField.getText().trim();
        if (!idPetugas.isEmpty()) {
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\rian yuliawan\\OneDrive\\Dokumen\\posyandu\\src\\com\\posyandu\\asset\\posyandu.db")) {
                String sql = "SELECT nama FROM data_petugas WHERE idPetugas = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, idPetugas);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        namaPetugasField.setText(rs.getString("nama"));
                    } else {
                        JOptionPane.showMessageDialog(this, "ID Petugas tidak ditemukan");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error fetching data: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "ID Petugas tidak boleh kosong");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DataImunisasiFormPage(new DataImunisasiPage());
            }
        });
    }
}
