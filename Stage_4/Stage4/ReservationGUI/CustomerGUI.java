import backend.BanderaCafeSystem;
import backend.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.List;

public class CustomerGUI extends JFrame {
    private BanderaCafeSystem cafeSystem; // Reference to the backend
    private DefaultTableModel tableModel; // For customer data table

    public CustomerGUI(BanderaCafeSystem cafeSystem) {
        this.cafeSystem = cafeSystem;

        // Set up the frame
        setTitle("Manage Customers");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table setup
        String[] columnNames = {"Customer ID", "Name", "Phone", "Email", "Address"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable customerTable = new JTable(tableModel);
        add(new JScrollPane(customerTable), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Customer");
        JButton editButton = new JButton("Edit Customer");
        JButton deleteButton = new JButton("Delete Customer");
        JButton loadButton = new JButton("Load Data");
        JButton saveButton = new JButton("Save Data");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(e -> addCustomer());
        editButton.addActionListener(e -> editCustomer(customerTable.getSelectedRow()));
        deleteButton.addActionListener(e -> deleteCustomer(customerTable.getSelectedRow()));
        loadButton.addActionListener(e -> loadData());
        saveButton.addActionListener(e -> saveData());

        // Load customers from backend
        loadCustomersFromBackend();
    }

    private void loadCustomersFromBackend() {
        // Fetch customers from the backend system and populate the table
        List<Customer> customers = cafeSystem.getCustomers();
        for (Customer customer : customers) {
            tableModel.addRow(new Object[]{
                customer.getCustomerID(),
                customer.getName(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getAddress()
            });
        }
    }

    private void addCustomer() {
        // Input dialog for customer details
        String name = JOptionPane.showInputDialog(this, "Enter customer name:");
        String phone = JOptionPane.showInputDialog(this, "Enter phone number:");
        String email = JOptionPane.showInputDialog(this, "Enter email:");
        String address = JOptionPane.showInputDialog(this, "Enter address:");

        if (name != null && phone != null && email != null && address != null) {
            int id = cafeSystem.getCustomers().size() + 1; // Generate a simple ID
            Customer customer = new Customer(id, name, phone, email, address);
            cafeSystem.getCustomers().add(customer); // Add to backend
            tableModel.addRow(new Object[]{id, name, phone, email, address});
        }
    }

    private void editCustomer(int selectedRow) {
        if (selectedRow >= 0) {
            List<Customer> customers = cafeSystem.getCustomers();
            Customer customer = customers.get(selectedRow);

            String name = JOptionPane.showInputDialog(this, "Edit customer name:", customer.getName());
            String phone = JOptionPane.showInputDialog(this, "Edit phone number:", customer.getPhone());
            String email = JOptionPane.showInputDialog(this, "Edit email:", customer.getEmail());
            String address = JOptionPane.showInputDialog(this, "Edit address:", customer.getAddress());

            if (name != null && phone != null && email != null && address != null) {
                customer.updateContactInfo(phone, email, address);
                tableModel.setValueAt(name, selectedRow, 1);
                tableModel.setValueAt(phone, selectedRow, 2);
                tableModel.setValueAt(email, selectedRow, 3);
                tableModel.setValueAt(address, selectedRow, 4);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a customer to edit.");
        }
    }

    private void deleteCustomer(int selectedRow) {
        if (selectedRow >= 0) {
            cafeSystem.getCustomers().remove(selectedRow); // Remove from backend
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a customer to delete.");
        }
    }

    private void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("customers.txt"))) {
            for (Customer customer : cafeSystem.getCustomers()) {
                writer.write(customer.getCustomerID() + "," + customer.getName() + "," + customer.getPhone() + "," +
                        customer.getEmail() + "," + customer.getAddress() + "\n");
            }
            JOptionPane.showMessageDialog(this, "Data saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data.");
        }
    }

    private void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("customers.txt"))) {
            String line;
            cafeSystem.getCustomers().clear(); // Clear backend data
            tableModel.setRowCount(0); // Clear table
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String phone = parts[2];
                String email = parts[3];
                String address = parts[4];
                Customer customer = new Customer(id, name, phone, email, address);
                cafeSystem.getCustomers().add(customer); // Add to backend
                tableModel.addRow(new Object[]{id, name, phone, email, address});
            }
            JOptionPane.showMessageDialog(this, "Data loaded successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading data.");
        }
    }
}
