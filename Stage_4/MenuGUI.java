import backend.BanderaCafeSystem;
import backend.Item;
import backend.Menu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class MenuGUI extends JFrame {
    private Menu menu; // Reference to the backend menu
    private DefaultTableModel tableModel; // For menu data table

    public MenuGUI(Menu menu) {
        this.menu = menu;

        // Set up the frame
        setTitle("Manage Menu");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table setup
        String[] columnNames = {"Item ID", "Name", "Price", "Category", "Available"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable menuTable = new JTable(tableModel);
        add(new JScrollPane(menuTable), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Item");
        JButton editButton = new JButton("Edit Item");
        JButton deleteButton = new JButton("Delete Item");
        JButton loadButton = new JButton("Load Data");
        JButton saveButton = new JButton("Save Data");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(e -> addItem());
        editButton.addActionListener(e -> editItem(menuTable.getSelectedRow()));
        deleteButton.addActionListener(e -> deleteItem(menuTable.getSelectedRow()));
        loadButton.addActionListener(e -> loadData());
        saveButton.addActionListener(e -> saveData());

        // Initialize table with existing items
        loadMenuData();
    }

    
    private void loadMenuData() {
        for (Item item : menu.getItems()) {
            tableModel.addRow(new Object[]{item.getItemID(), item.getName(), item.getPrice(), item.getCategory(), item.isAvailable()});
        }
    }

    private void addItem() {
        String name = JOptionPane.showInputDialog(this, "Enter item name:");
        String priceStr = JOptionPane.showInputDialog(this, "Enter price:");
        String category = JOptionPane.showInputDialog(this, "Enter category:");
        boolean available = JOptionPane.showConfirmDialog(this, "Is the item available?") == JOptionPane.YES_OPTION;

        if (name != null && priceStr != null && category != null) {
            try {
                double price = Double.parseDouble(priceStr);
                int id = menu.getItems().size() + 1; // Generate a simple ID
                Item item = new Item(id, name, price, category, available);
                menu.addItem(item);
                tableModel.addRow(new Object[]{id, name, price, category, available});
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid price.");
            }
        }
    }

    private void editItem(int selectedRow) {
        if (selectedRow >= 0) {
            String name = JOptionPane.showInputDialog(this, "Edit item name:", tableModel.getValueAt(selectedRow, 1));
            String priceStr = JOptionPane.showInputDialog(this, "Edit price:", tableModel.getValueAt(selectedRow, 2));
            String category = JOptionPane.showInputDialog(this, "Edit category:", tableModel.getValueAt(selectedRow, 3));
            boolean available = JOptionPane.showConfirmDialog(this, "Is the item available?") == JOptionPane.YES_OPTION;

            if (name != null && priceStr != null && category != null) {
                try {
                    double price = Double.parseDouble(priceStr);
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    Item item = new Item(id, name, price, category, available);
                    menu.getItems().set(selectedRow, item); // Update in backend
                    tableModel.setValueAt(name, selectedRow, 1);
                    tableModel.setValueAt(price, selectedRow, 2);
                    tableModel.setValueAt(category, selectedRow, 3);
                    tableModel.setValueAt(available, selectedRow, 4);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid price.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to edit.");
        }
    }

    private void deleteItem(int selectedRow) {
        if (selectedRow >= 0) {
            menu.getItems().remove(selectedRow);
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to delete.");
        }
    }

    private void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("menu.txt"))) {
            for (Item item : menu.getItems()) {
                writer.write(item.getItemID() + "," + item.getName() + "," + item.getPrice() + "," +
                        item.getCategory() + "," + item.isAvailable() + "\n");
            }
            JOptionPane.showMessageDialog(this, "Data saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data.");
        }
    }

    private void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("menu.txt"))) {
            String line;
            menu.getItems().clear();
            tableModel.setRowCount(0); // Clear table
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                String category = parts[3];
                boolean available = Boolean.parseBoolean(parts[4]);
                Item item = new Item(id, name, price, category, available);
                menu.addItem(item);
                tableModel.addRow(new Object[]{id, name, price, category, available});
            }
            JOptionPane.showMessageDialog(this, "Data loaded successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading data.");
        }
    }
}
