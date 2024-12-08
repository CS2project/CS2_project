import backend.BanderaCafeSystem;
import backend.Customer;
import backend.Reservation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationGUI extends JFrame {
    private DefaultTableModel tableModel; // For reservation data table
    private List<Reservation> reservations; // List of reservations
    private BanderaCafeSystem cafeSystem; // Reference to the backend system

    public ReservationGUI(BanderaCafeSystem cafeSystem) {
        this.cafeSystem = cafeSystem;

        // Set up the frame
        setTitle("Manage Reservations");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table setup
        String[] columnNames = {"Reservation ID", "Customer ID", "Table ID", "Date", "Time"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable reservationTable = new JTable(tableModel);
        add(new JScrollPane(reservationTable), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Reservation");
        JButton editButton = new JButton("Edit Reservation");
        JButton deleteButton = new JButton("Delete Reservation");
        JButton loadButton = new JButton("Load Data");
        JButton saveButton = new JButton("Save Data");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(e -> addReservation());
        editButton.addActionListener(e -> editReservation(reservationTable.getSelectedRow()));
        deleteButton.addActionListener(e -> deleteReservation(reservationTable.getSelectedRow()));
        loadButton.addActionListener(e -> loadData());
        saveButton.addActionListener(e -> saveData());

        // Initialize reservation list
        reservations = new ArrayList<>();

        // Load reservations from backend on startup
        loadReservationsFromBackend();
    }

    private void loadReservationsFromBackend() {
        reservations = cafeSystem.getReservation(); // Get reservations from backend
        tableModel.setRowCount(0); // Clear existing rows
        for (Reservation reservation : reservations) {
            tableModel.addRow(new Object[]{
                reservation.getReservationID(),
                reservation.getCustomerID(),
                reservation.getTableID(),
                reservation.getDate(),
                reservation.getTime()
            });
        }
    }

    private void addReservation() {
        try {
            int reservationID = reservations.size() + 1; // Generate a simple ID
            int customerID = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Customer ID:"));
            int tableID = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Table ID:"));
            String date = JOptionPane.showInputDialog(this, "Enter Date (YYYY-MM-DD):");
            String time = JOptionPane.showInputDialog(this, "Enter Time (HH:MM):");

            if (date != null && time != null) {
                Reservation reservation = new Reservation(reservationID, customerID, tableID, date, time);
                reservations.add(reservation);
                tableModel.addRow(new Object[]{reservationID, customerID, tableID, date, time});
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers for IDs.");
        }
    }

    private void editReservation(int selectedRow) {
        if (selectedRow >= 0) {
            Reservation reservation = reservations.get(selectedRow);
            String date = JOptionPane.showInputDialog(this, "Edit Date (YYYY-MM-DD):", reservation.getDate());
            String time = JOptionPane.showInputDialog(this, "Edit Time (HH:MM):", reservation.getTime());

            if (date != null && time != null) {
                reservation.updateReservation(date, time);
                tableModel.setValueAt(date, selectedRow, 3);
                tableModel.setValueAt(time, selectedRow, 4);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a reservation to edit.");
        }
    }

    private void deleteReservation(int selectedRow) {
        if (selectedRow >= 0) {
            reservations.remove(selectedRow);
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a reservation to delete.");
        }
    }

    private void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reservations.txt"))) {
            for (Reservation reservation : reservations) {
                writer.write(reservation.getReservationID() + "," + reservation.getCustomerID() + "," +
                        reservation.getTableID() + "," + reservation.getDate() + "," + reservation.getTime() + "\n");
            }
            JOptionPane.showMessageDialog(this, "Data saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data.");
        }
    }

    private void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("reservations.txt"))) {
            String line;
            reservations.clear();
            tableModel.setRowCount(0); // Clear table
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int reservationID = Integer.parseInt(parts[0]);
                int customerID = Integer.parseInt(parts[1]);
                int tableID = Integer.parseInt(parts[2]);
                String date = parts[3];
                String time = parts[4];
                Reservation reservation = new Reservation(reservationID, customerID, tableID, date, time);
                reservations.add(reservation);
                tableModel.addRow(new Object[]{reservationID, customerID, tableID, date, time});
            }
            JOptionPane.showMessageDialog(this, "Data loaded successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading data.");
        }
    }
}
