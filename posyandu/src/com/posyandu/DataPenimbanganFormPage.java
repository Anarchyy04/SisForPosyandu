package com.posyandu;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class DataPenimbanganFormPage extends JFrame {
    private JTextField nikField, namaField, umurField, beratBadanField, tinggiBadanField, statusGiziField, catatanField;
    private JTextField idPetugasField, namaPetugasField;
    private JButton saveButton, searchNikButton, searchIdPetugasButton, hitungButton;
    private JDateChooser dateChooser;
    private JComboBox<String> monthComboBox;
    private DataPenimbanganPage parentPage;

    public DataPenimbanganFormPage(DataPenimbanganPage parent) {
        this.parentPage = parent;

        setTitle("Formulir Data Penimbangan");
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
        JLabel formTitle = new JLabel("Formulir Data Penimbangan", JLabel.CENTER);
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

        addLabeledField(formPanel, "NIK:", gbc, 2, nikField = new JTextField(20), searchNikButton = new JButton("Cari"));
        addLabeledField(formPanel, "Nama:", gbc, 4, namaField = new JTextField(20));
        addLabeledField(formPanel, "Tanggal Lahir:", gbc, 6, umurField = new JTextField(20));

        // Make namaField and umurField non-editable
        namaField.setEditable(false);
        umurField.setEditable(false);

        // Section II: Data Penimbangan
        JLabel dataPenimbanganLabel = new JLabel("II. Data Penimbangan");
        dataPenimbanganLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        dataPenimbanganLabel.setForeground(new Color(34, 139, 34));
        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(dataPenimbanganLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(new JLabel(""), gbc);

        gbc.gridwidth = 2;
        dateChooser = new JDateChooser();
        addLabeledField(formPanel, "Tanggal:", gbc, 9, dateChooser);
        addLabeledField(formPanel, "Bulan ke-:", gbc, 10, monthComboBox = createMonthComboBox());
        gbc.gridwidth = 1;

        addLabeledField(formPanel, "BB:", gbc, 11, beratBadanField = new JTextField(20));

        gbc.gridx = 0;
        gbc.gridy = 13;
        formPanel.add(createLabel("TB:"), gbc);
        gbc.gridx = 1;
        tinggiBadanField = new JTextField(20);
        JPanel tbPanel = new JPanel(new BorderLayout());
        tbPanel.add(tinggiBadanField, BorderLayout.CENTER);
        hitungButton = new JButton("Hitung");
        tbPanel.add(hitungButton, BorderLayout.EAST);
        formPanel.add(tbPanel, gbc);

        addLabeledField(formPanel, "Status Gizi:", gbc, 15, statusGiziField = new JTextField(20));
        addLabeledField(formPanel, "Catatan:", gbc, 17, catatanField = new JTextField(20));

        // Section III: Data Petugas
        JLabel dataPetugasLabel = new JLabel("III. Data Petugas");
        dataPetugasLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        dataPetugasLabel.setForeground(new Color(34, 139, 34));
        gbc.gridx = 0;
        gbc.gridy = 19;
        formPanel.add(dataPetugasLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(new JLabel(""), gbc);

        addLabeledField(formPanel, "ID Petugas:", gbc, 20, idPetugasField = new JTextField(20), searchIdPetugasButton = new JButton("Cari"));
        addLabeledField(formPanel, "Nama Petugas:", gbc, 22, namaPetugasField = new JTextField(20));

        // Make namaPetugasField non-editable
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

        searchNikButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchAnakData();
            }
        });

        searchIdPetugasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchPetugasData();
            }
        });

        hitungButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitungStatusGizi();
            }
        });

        addKeyListeners();
        setVisible(true);
    }

    private void addLabeledField(JPanel panel, String labelText, GridBagConstraints gbc, int yPos, JComponent field) {
        addLabeledField(panel, labelText, gbc, yPos, field, null);
    }

    private void addLabeledField(JPanel panel, String labelText, GridBagConstraints gbc, int yPos, JComponent field, JButton button) {
        gbc.gridx = 0;
        gbc.gridy = yPos;
        panel.add(createLabel(labelText), gbc);
        gbc.gridx = 1;
        gbc.gridy = yPos;
        if (button != null) {
            JPanel panelWithButton = new JPanel(new BorderLayout());
            panelWithButton.add(field, BorderLayout.CENTER);
            panelWithButton.add(button, BorderLayout.EAST);
            panel.add(panelWithButton, gbc);
        } else {
            panel.add(field, gbc);
        }
    }

    private JComboBox<String> createMonthComboBox() {
        String[] months = {
            "Bulan Pertama", "Bulan Kedua", "Bulan Ketiga", "Bulan Keempat",
            "Bulan Kelima", "Bulan Keenam"
        };
        JComboBox<String> comboBox = new JComboBox<>(months);
        comboBox.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        comboBox.setForeground(new Color(34, 139, 34));
        return comboBox;
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
        String umur = umurField.getText();
        String bb = beratBadanField.getText();
        String tb = tinggiBadanField.getText();
        String statusGizi = statusGiziField.getText();
        String catatan = catatanField.getText();
        String idPetugas = idPetugasField.getText();
        String namaPetugas = namaPetugasField.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String tanggal = dateFormat.format(dateChooser.getDate());
        String bulan = (String) monthComboBox.getSelectedItem();

        String sql = "INSERT INTO data_penimbangan (nik, nama, tanggal_lahir, tanggal, bulan, bb, tb, status_gizi, catatan, id_petugas, nama_petugas) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nik);
            statement.setString(2, nama);
            statement.setString(3, umur);
            statement.setString(4, tanggal);
            statement.setString(5, bulan);
            statement.setString(6, bb);
            statement.setString(7, tb);
            statement.setString(8, statusGizi);
            statement.setString(9, catatan);
            statement.setString(10, idPetugas);
            statement.setString(11, namaPetugas);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }

        // Add data to parent table
        parentPage.addWeightData(nik, nama, umur, tanggal, bulan, bb, tb, statusGizi, catatan, idPetugas, namaPetugas);

        // Close the form
        dispose();
    }

    private void clearFields() {
        nikField.setText("");
        namaField.setText("");
        umurField.setText("");
        dateChooser.setDate(null);
        monthComboBox.setSelectedIndex(0);
        beratBadanField.setText("");
        tinggiBadanField.setText("");
        statusGiziField.setText("");
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
        umurField.addKeyListener(keyAdapter);
        beratBadanField.addKeyListener(keyAdapter);
        tinggiBadanField.addKeyListener(keyAdapter);
        statusGiziField.addKeyListener(keyAdapter);
        catatanField.addKeyListener(keyAdapter);
        idPetugasField.addKeyListener(keyAdapter);
        namaPetugasField.addKeyListener(keyAdapter);
        dateChooser.getDateEditor().getUiComponent().addKeyListener(keyAdapter);
        monthComboBox.addKeyListener(keyAdapter);
    }

    private void checkFields() {
        boolean allFieldsFilled = !nikField.getText().trim().isEmpty() && !namaField.getText().trim().isEmpty()
                && !umurField.getText().trim().isEmpty() && dateChooser.getDate() != null
                && monthComboBox.getSelectedIndex() != -1 && !beratBadanField.getText().trim().isEmpty()
                && !tinggiBadanField.getText().trim().isEmpty() && !statusGiziField.getText().trim().isEmpty()
                && !catatanField.getText().trim().isEmpty() && !idPetugasField.getText().trim().isEmpty()
                && !namaPetugasField.getText().trim().isEmpty();
        saveButton.setEnabled(allFieldsFilled);
    }

    private void fetchAnakData() {
        String nik = nikField.getText();
        String sql = "SELECT nama, tanggal_lahir FROM data_anak WHERE nik = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nik);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                namaField.setText(resultSet.getString("nama"));
                umurField.setText(resultSet.getString("tanggal_lahir"));
            } else {
                JOptionPane.showMessageDialog(this, "Data tidak ditemukan.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void fetchPetugasData() {
        String idPetugas = idPetugasField.getText();
        String sql = "SELECT nama FROM data_petugas WHERE idPetugas = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idPetugas);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                namaPetugasField.setText(resultSet.getString("nama"));
            } else {
                JOptionPane.showMessageDialog(this, "Data tidak ditemukan.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void hitungStatusGizi() {
        try {
            double beratBadan = Double.parseDouble(beratBadanField.getText());
            double tinggiBadan = Double.parseDouble(tinggiBadanField.getText());

            // Calculate BMI
            double bmi = beratBadan / Math.pow(tinggiBadan / 100, 2);

            // Determine nutritional status based on the selected month
            String bulan = (String) monthComboBox.getSelectedItem();
            String statusGizi = "";

            switch (bulan) {
                case "Bulan Pertama":
                    if (bmi >= 11.1 && bmi <= 16.3) {
                        statusGizi = "Normal";
                    } else if (bmi < 11.1) {
                        statusGizi = "Kurang Gizi";
                    } else {
                        statusGizi = "Kelebihan Gizi";
                    }
                    break;
                case "Bulan Kedua":
                    if (bmi >= 12.4 && bmi <= 17.8) {
                        statusGizi = "Normal";
                    } else if (bmi < 12.4) {
                        statusGizi = "Kurang Gizi";
                    } else {
                        statusGizi = "Kelebihan Gizi";
                    }
                    break;
                case "Bulan Ketiga":
                    if (bmi >= 14.3 && bmi <= 20) {
                        statusGizi = "Normal";
                    } else if (bmi < 14.3) {
                        statusGizi = "Kurang Gizi";
                    } else {
                        statusGizi = "Kelebihan Gizi";
                    }
                    break;
                case "Bulan Keempat":
                    if (bmi >= 14.5 && bmi <= 20.3) {
                        statusGizi = "Normal";
                    } else if (bmi < 14.5) {
                        statusGizi = "Kurang Gizi";
                    } else {
                        statusGizi = "Kelebihan Gizi";
                    }
                    break;
                case "Bulan Kelima":
                    if (bmi >= 14.7 && bmi <= 20.5) {
                        statusGizi = "Normal";
                    } else if (bmi < 14.7) {
                        statusGizi = "Kurang Gizi";
                    } else {
                        statusGizi = "Kelebihan Gizi";
                    }
                    break;
                case "Bulan Keenam":
                    if (bmi >= 14.7 && bmi <= 20.5) {
                        statusGizi = "Normal";
                    } else if (bmi < 14.7) {
                        statusGizi = "Kurang Gizi";
                    } else {
                        statusGizi = "Kelebihan Gizi";
                    }
                    break;
                default:
                    statusGizi = "Data Bulan Tidak Valid";
                    break;
            }

            // Set the status gizi field
            statusGiziField.setText(statusGizi);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Input berat badan dan tinggi badan harus berupa angka.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DataPenimbanganFormPage(new DataPenimbanganPage());
            }
        });
    }
}

class DataPenimbanganPage {
    public void addWeightData(String nik, String nama, String umur, String tanggal, String bulan, String bb, String tb, String statusGizi, String catatan, String idPetugas, String namaPetugas) {
        // Logic to add weight data
    }
}






