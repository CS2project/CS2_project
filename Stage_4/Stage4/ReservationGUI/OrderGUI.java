import backend.Item;
import backend.Menu;
import backend.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrderGUI extends JFrame {
    private List<Order> orders; // List of orders
    private Menu menu; // Reference to the menu for item lookup
    private DefaultTableModel tableModel; // For order data table

    public OrderGUI(Menu menu) {
        this.menu = menu;

        // Set up the frame
        setTitle("Manage Orders");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table setup
        String[] columnNames = {"Order ID", "Table ID", "Items"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable orderTable = new JTable(tableModel);
        add(new JScrollPane(orderTable), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton newOrderButton = new JButton("New Order");
        JButton addItemButton = new JButton("Add Item");
        JButton removeItemButton = new JButton("Remove Item");
        JButton deleteOrderButton = new JButton("Delete Order");
        JButton loadButton = new JButton("Load Data");
        JButton saveButton = new JButton("Save Data");
        buttonPanel.add(newOrderButton);
        buttonPanel.add(addItemButton);
        buttonPanel.add(removeItemButton);
        buttonPanel.add(deleteOrderButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        newOrderButton.addActionListener(e -> createNewOrder());
        addItemButton.addActionListener(e -> addItemToOrder(orderTable.getSelectedRow()));
        removeItemButton.addActionListener(e -> removeItemFromOrder(orderTable.getSelectedRow()));
        deleteOrderButton.addActionListener(e -> deleteOrder(orderTable.getSelectedRow()));
        loadButton.addActionListener(e -> loadData());
        saveButton.addActionListener(e -> saveData());

        // Initialize order list
        orders = new ArrayList<>();
    }

    private void createNewOrder() {
        int orderID = orders.size() + 1;
        int tableID = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Table ID:"));
        Order newOrder = new Order(orderID, tableID);
        orders.add(newOrder);
        tableModel.addRow(new Object[]{orderID, tableID, "No items yet"});
    }

    private void addItemToOrder(int selectedRow) {
        if (selectedRow >= 0) {
            String itemIDStr = JOptionPane.showInputDialog(this, "Enter Item ID to Add:");
            try {
                int itemID = Integer.parseInt(itemIDStr);
                Item item = menu.getItems().stream()
                        .filter(i -> i.getItemID() == itemID)
                        .findFirst()
                        .orElse(null);
                if (item != null) {
                    Order order = orders.get(selectedRow);
                    order.addItem(item);
                    tableModel.setValueAt(order.getItems().toString(), selectedRow, 2);
                } else {
                    JOptionPane.showMessageDialog(this, "Item not found in the menu.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Item ID.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an order to add items.");
        }
    }

    private void removeItemFromOrder(int selectedRow) {
        if (selectedRow >= 0) {
            Order order = orders.get(selectedRow);
            String itemIDStr = JOptionPane.showInputDialog(this, "Enter Item ID to Remove:");
            try {
                int itemID = Integer.parseInt(itemIDStr);
                Item item = menu.getItems().stream()
                        .filter(i -> i.getItemID() == itemID)
                        .findFirst()
                        .orElse(null);
                if (item != null) {
                    order.removeItem(item);
                    tableModel.setValueAt(order.getItems().toString(), selectedRow, 2);
                } else {
                    JOptionPane.showMessageDialog(this, "Item not found in the order.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Item ID.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an order to remove items.");
        }
    }

    private void deleteOrder(int selectedRow) {
        if (selectedRow >= 0) {
            orders.remove(selectedRow);
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an order to delete.");
        }
    }

    private void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("orders.txt"))) {
            for (Order order : orders) {
                writer.write(order.getOrderID() + "," + order.getTableID() + "," + order.getItems().toString() + "\n");
            }
            JOptionPane.showMessageDialog(this, "Data saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data.");
        }
    }

    private void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("orders.txt"))) {
            String line;
            orders.clear();
            tableModel.setRowCount(0);
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 3);
                int orderID = Integer.parseInt(parts[0]);
                int tableID = Integer.parseInt(parts[1]);
                Order order = new Order(orderID, tableID);
                orders.add(order);
                tableModel.addRow(new Object[]{orderID, tableID, parts[2]});
            }
            JOptionPane.showMessageDialog(this, "Data loaded successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading data.");
        }
    }
}
