import backend.BanderaCafeSystem;
import backend.Schedule;
import backend.Staff;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StaffGUI extends JFrame {
    private DefaultTableModel tableModel; // For staff data table
    private List<Staff> staffList; // List of staff members
    private BanderaCafeSystem cafeSystem; 

    public StaffGUI(BanderaCafeSystem cafeSystem) {
        this.cafeSystem= cafeSystem;
        // Set up the frame
        setTitle("Manage Staff");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table setup
        String[] columnNames = {"Staff ID", "Name", "Role", "Phone", "Email", "Schedule"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable staffTable = new JTable(tableModel);
        add(new JScrollPane(staffTable), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Staff");
        JButton editButton = new JButton("Edit Staff");
        JButton deleteButton = new JButton("Delete Staff");
        JButton loadButton = new JButton("Load Data");
        JButton saveButton = new JButton("Save Data");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(e -> addStaff());
        editButton.addActionListener(e -> editStaff(staffTable.getSelectedRow()));
        deleteButton.addActionListener(e -> deleteStaff(staffTable.getSelectedRow()));
        loadButton.addActionListener(e -> loadData());
        saveButton.addActionListener(e -> saveData());

        // Initialize staff list
        staffList = new ArrayList<>();
        
        loadStaffFromBackend();
    }

    private void addStaff() {
        String name = JOptionPane.showInputDialog(this, "Enter staff name:");
        String role = JOptionPane.showInputDialog(this, "Enter role:");
        String phone = JOptionPane.showInputDialog(this, "Enter phone number:");
        String email = JOptionPane.showInputDialog(this, "Enter email:");
        String scheduleInfo = JOptionPane.showInputDialog(this, "Enter schedule (e.g., Monday Morning):");

        if (name != null && role != null && phone != null && email != null && scheduleInfo != null) {
            int id = staffList.size() + 1; // Generate a simple ID
            Schedule schedule = new Schedule(id, scheduleInfo.split(" ")[0], scheduleInfo.split(" ")[1]);
            Staff staff = new Staff(id, name, role, phone, email, schedule);
            staffList.add(staff);
            tableModel.addRow(new Object[]{id, name, role, phone, email, scheduleInfo});
        }
    }

    private void editStaff(int selectedRow) {
        if (selectedRow >= 0) {
            Staff staff = staffList.get(selectedRow);
            String name = JOptionPane.showInputDialog(this, "Edit staff name:", staff.getName());
            String role = JOptionPane.showInputDialog(this, "Edit role:", staff.getRole());
            String phone = JOptionPane.showInputDialog(this, "Edit phone:", staff.getName());
            String email = JOptionPane.showInputDialog(this, "Edit email:", staff.getName());
            String scheduleInfo = JOptionPane.showInputDialog(this, "Edit schedule:", staff.getRole());

            if (name != null && role != null && phone != null && email != null && scheduleInfo != null) {
                staff.updateContactInfo(phone, email, ""); // Update only phone/email
                Schedule schedule = new Schedule(staff.getStaffID(), scheduleInfo.split(" ")[0], scheduleInfo.split(" ")[1]);
                staffList.set(selectedRow, new Staff(staff.getStaffID(), name, role, phone, email, schedule));
                tableModel.setValueAt(name, selectedRow, 1);
                tableModel.setValueAt(role, selectedRow, 2);
                tableModel.setValueAt(phone, selectedRow, 3);
                tableModel.setValueAt(email, selectedRow, 4);
                tableModel.setValueAt(scheduleInfo, selectedRow, 5);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a staff member to edit.");
        }
    }

    private void deleteStaff(int selectedRow) {
        if (selectedRow >= 0) {
            staffList.remove(selectedRow);
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a staff member to delete.");
        }
    }

    private void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("staff.txt"))) {
            for (Staff staff : staffList) {
                writer.write(staff.getStaffID() + "," + staff.getName() + "," + staff.getRole() + "," +
                        staff.getRole() + "," + staff.getEmail() + "," + staff.getRole() + "\n");
            }
            JOptionPane.showMessageDialog(this, "Data saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data.");
        }
    }

    private void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("staff.txt"))) {
            String line;
            staffList.clear();
            tableModel.setRowCount(0); // Clear table
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 6);
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String role = parts[2];
                String phone = parts[3];
                String email = parts[4];
                String scheduleInfo = parts[5];
                Schedule schedule = new Schedule(id, scheduleInfo.split(" ")[0], scheduleInfo.split(" ")[1]);
                Staff staff = new Staff(id, name, role, phone, email, schedule);
                staffList.add(staff);
                tableModel.addRow(new Object[]{id, name, role, phone, email, scheduleInfo});
            }
            JOptionPane.showMessageDialog(this, "Data loaded successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading data.");
        }
    }

    private void loadStaffFromBackend() {
        staffList = cafeSystem.getStaff(); // Fetch the staff list from backend
    for (Staff employee : staffList) {
        tableModel.addRow(new Object[]{
            employee.getStaffID(),
            employee.getName(),
            employee.getRole(),
            employee.getPhone(),
            employee.getEmail(),
            employee.getSchedule().getDayOfWeek() + " " + employee.getSchedule().getShiftType()
        });
    }
    }
}
