package com.posyandu;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataPenimbanganPage extends JFrame {
    private JTextField searchField;
    private JTable dataTable;
    private DefaultTableModel tableModel;

    public DataPenimbanganPage() {
        // Set up the frame
        setTitle("Data Penimbangan");
        setSize(1020, 560);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(255, 223, 186));
        add(panel);

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Data Penimbangan");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        titleLabel.setForeground(new Color(34, 139, 34));

        headerPanel.add(titleLabel);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(255, 223, 186));
        contentPanel.setLayout(new BorderLayout(10, 10));
        panel.add(contentPanel, BorderLayout.CENTER);

        // Top Panel for Buttons and Search
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(new Color(255, 223, 186));
        contentPanel.add(topPanel, BorderLayout.NORTH);

        // Add Button Panel
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButtonPanel.setBackground(new Color(255, 223, 186));
        JButton addButton = new JButton("Tambah Data Penimbangan");
        addButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        addButton.setBackground(new Color(30, 144, 255));
        addButton.setForeground(Color.WHITE);
        addButtonPanel.add(addButton);
        topPanel.add(addButtonPanel, BorderLayout.WEST);

        // Add button action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DataPenimbanganFormPage(DataPenimbanganPage.this);
            }
        });

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(new Color(255, 223, 186));
        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));
        JButton searchButton = new JButton("Cari Data");
        searchButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        searchButton.setBackground(new Color(30, 144, 255));
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
        String[] columnNames = {"NIK", "Nama", "Tanggal Lahir", "Tanggal", "Bulan ke-", "BB", "TB", "Status Gizi", "Catatan", "ID Petugas", "Nama Petugas"};
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
        backButton.setBackground(new Color(30, 144, 255));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainPage();
                dispose();
            }
        });
        panel.add(backButton, BorderLayout.SOUTH);

        // Load data from database
        loadData();

        // Make the frame visible
        setVisible(true);
    }

    private void filterTable(String searchText) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        dataTable.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
    }

    public void addWeightData(String nik, String nama, String tanggalLahir, String tanggal, String bulan, String bbPenimbangan, String tbPenimbangan, String statusGizi, String catatan, String idPetugas, String namaPetugas) {
        tableModel.addRow(new Object[]{nik, nama, tanggalLahir, tanggal, bulan, bbPenimbangan, tbPenimbangan, statusGizi, catatan, idPetugas, namaPetugas});
    }

    private void loadData() {
        String sql = "SELECT * FROM data_penimbangan";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String nik = resultSet.getString("nik");
                String nama = resultSet.getString("nama");
                String tanggalLahir = resultSet.getString("tanggal_lahir");
                String tanggal = resultSet.getString("tanggal");
                String bulan = resultSet.getString("bulan");
                String bb = resultSet.getString("bb");
                String tb = resultSet.getString("tb");
                String statusGizi = resultSet.getString("status_gizi");
                String catatan = resultSet.getString("catatan");
                String idPetugas = resultSet.getString("id_petugas");
                String namaPetugas = resultSet.getString("nama_petugas");

                tableModel.addRow(new Object[]{nik, nama, tanggalLahir, tanggal, bulan, bb, tb, statusGizi, catatan, idPetugas, namaPetugas});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DataPenimbanganPage();
            }
        });
    }
}
