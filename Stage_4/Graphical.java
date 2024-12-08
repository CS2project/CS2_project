import backend.BanderaCafeSystem;
import backend.Menu;

import javax.swing.*;
import java.awt.*;

public class Graphical extends JFrame {
    private BanderaCafeSystem cafeSystem; // Reference to the backend system

    public Graphical(BanderaCafeSystem cafeSystem) {
        this.cafeSystem = cafeSystem; // Assign the backend system reference

        // Set up the main frame
        setTitle("Bandera Cafe Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10)); // Grid layout for buttons

        // Create navigation buttons
        JButton customerButton = new JButton("Manage Customers");
        JButton menuButton = new JButton("Manage Menu");
        JButton orderButton = new JButton("Manage Orders");
        JButton staffButton = new JButton("Manage Staff");
        JButton reservationButton = new JButton("Manage Reservations");
        JButton salesReportButton = new JButton("View Sales Report");

        // Add buttons to the frame
        add(customerButton);
        add(menuButton);
        add(orderButton);
        add(staffButton);
        add(reservationButton);
        add(salesReportButton);

        // Add action listeners to buttons
        customerButton.addActionListener(e -> new CustomerGUI(cafeSystem).setVisible(true));

        // Pass the menu object from the backend system to MenuGUI
        menuButton.addActionListener(e -> new MenuGUI(cafeSystem.getMenu()).setVisible(true));

        // Pass the menu object to OrderGUI
        orderButton.addActionListener(e -> new OrderGUI(cafeSystem.getMenu()).setVisible(true));

        // Pass the backend system to StaffGUI
        staffButton.addActionListener(e -> new StaffGUI(cafeSystem).setVisible(true));

        // Pass the backend system to ReservationGUI
        reservationButton.addActionListener(e -> new ReservationGUI(cafeSystem).setVisible(true));

        // Pass the completed orders to SalesReportGUI
        salesReportButton.addActionListener(e -> new SalesReportGUI(cafeSystem.getCompletedOrders()).setVisible(true));
    }

    public static void main(String[] args) {
        // Create backend instance
        BanderaCafeSystem cafeSystem = new BanderaCafeSystem();

        // Pre-load menu items
        cafeSystem.getMenu().addItem(new backend.Item(1, "Espresso", 3.0, "Beverage", true));
        cafeSystem.getMenu().addItem(new backend.Item(2, "Cappuccino", 4.5, "Beverage", true));
        cafeSystem.getMenu().addItem(new backend.Item(3, "Latte", 4.0, "Beverage", true));
        cafeSystem.getMenu().addItem(new backend.Item(4, "Croissant", 3.0, "Snack", true));
        cafeSystem.getMenu().addItem(new backend.Item(5, "Blueberry Muffin", 2.5, "Snack", true));

        // Show the main frame
        Graphical mainFrame = new Graphical(cafeSystem);
        mainFrame.setVisible(true);
    }
}
