package com.posyandu;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataAnakPage extends JFrame {
    private JTextField searchField;
    private JTable dataTable;
    private DefaultTableModel tableModel;

    public DataAnakPage() {
        // Set up the frame
        setTitle("Data Anak");
        setSize(1020, 560);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(255, 223, 186)); // Lighter and more cheerful background color
        add(panel);

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Data Anak");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 36)); // More cheerful and larger font
        titleLabel.setForeground(new Color(34, 139, 34));

        headerPanel.add(titleLabel);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(255, 223, 186)); // Same background color
        contentPanel.setLayout(new BorderLayout(10, 10));
        panel.add(contentPanel, BorderLayout.CENTER);

        // Top Panel for Buttons and Search
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(new Color(255, 223, 186));
        contentPanel.add(topPanel, BorderLayout.NORTH);

        // Add Button Panel
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButtonPanel.setBackground(new Color(255, 223, 186));
        JButton addButton = new JButton("Tambah Data Anak");
        addButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        addButton.setBackground(new Color(135, 206, 250));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DataAnakFormPage(DataAnakPage.this);
                dispose();
            }
        });
        addButtonPanel.add(addButton);
        topPanel.add(addButtonPanel, BorderLayout.WEST);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(new Color(255, 223, 186));
        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));
        JButton searchButton = new JButton("Cari Data");
        searchButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        searchButton.setBackground(new Color(135, 206, 250));
        searchButton.setForeground(Color.WHITE);
        searchButton.setPreferredSize(new Dimension(200, 30));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to search data in the table
                String searchText = searchField.getText().toLowerCase();
                filterTable(searchText);
            }
        });

        searchPanel.add(new JLabel("Cari Data: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        topPanel.add(searchPanel, BorderLayout.EAST);

        // Table
        String[] columnNames = {"Nama", "NIK", "Tanggal Lahir", "Jenis Kelamin", "Nama Orang Tua", "No Telpon", "Alamat Lengkap", "Berat Badan", "Tinggi Badan", "Lingkar Kepala", "Riwayat Imunisasi", "Riwayat Penyakit"};
        tableModel = new DefaultTableModel(columnNames, 0);
        dataTable = new JTable(tableModel);

        // Enable text wrapping for table cells
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public void setValue(Object value) {
                setText(value == null ? "" : "<html><body style='width: 100px;'>" + value.toString() + "</body></html>");
            }
        };
        for (int i = 0; i < columnNames.length; i++) {
            dataTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        JScrollPane scrollPane = new JScrollPane(dataTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Back Button
        JButton backButton = new JButton("Back to Main Page");
        backButton.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        backButton.setBackground(new Color(135, 206, 250));
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

    private void filterTable(String searchText) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        dataTable.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
    }

    public void addChildData(String nama, String nik, String tanggalLahir, String jenisKelamin, String namaOrtu, String noTelpon, String alamat, String beratBadan, String tinggiBadan, String lingkarKepala, String riwayatImunisasi, String riwayatPenyakit) {
        tableModel.addRow(new Object[]{nama, nik, tanggalLahir, jenisKelamin, namaOrtu, noTelpon, alamat, beratBadan, tinggiBadan, lingkarKepala, riwayatImunisasi, riwayatPenyakit});
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DataAnakPage();
            }
        });
    }
}
