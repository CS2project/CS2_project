/**
 * Bandera Cafe restaurant System
 * For cs 234
 * @auther Ayham Abbad
 * @auther Brenda Vanessa
 * @auther Andrew Boese
 */
/**
 * The main class for the Bandera Cafe System application.
 * Initializes and runs the cafe system, setting up the menu, tables, staff, and customer interactions.
 * It includes main operations such as order processing, reservations, and payment handling.
 */
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class BanderaCafeSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static Menu menu = new Menu();
    private static Customer customer1 = new Customer(1, "Ayham Abbad", "123-456-7890", "ayham@example.com", "001 Road St");
    private static Customer customer2 = new Customer(2, "Andrew Boese", "111-222-333", "andrew@example.com", "123 Lane St");
    private static Table table = new Table(1, 4);
    private static Staff staff = new Staff(1, "Brenda", "Barista", "000-000-000", "brenda@example.com", new Schedule(1, "Monday", "Morning"));
    private static Order order = new Order(1001, table.getTableID());
    private static Reservation reservation = new Reservation(2001, customer1.getCustomerID(), table.getTableID(), "2024-11-10", "06:00");
    private static List<Order> completedOrders = new ArrayList<>(); // List to store completed orders

    public static void main(String[] args) {
        initializeData();
        showMainMenu();
    }

    private static void initializeData() {
        // Add available items to the menu
        menu.addItem(new Item(1, "Espresso", 3.00, "Beverage", true));
        menu.addItem(new Item(2, "Cappuccino", 4.50, "Beverage", true));
        menu.addItem(new Item(3, "Latte", 4.00, "Beverage", true));
        menu.addItem(new Item(4, "Blueberry Muffin", 2.50, "Snack", true));
        menu.addItem(new Item(5, "Croissant", 3.00, "Snack", true));
        menu.addItem(new Item(6, "Chocolate Cake Slice", 4.00, "Dessert", true));
        order.addItem(menu.getItems().get(0));  // Add an item to order for initial data
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("\n--- Bandera Cafe Management System ---");
            System.out.println("1. View Customers");
            System.out.println("2. View Menu");
            System.out.println("3. Manage Orders");
            System.out.println("4. Manage Tables");
            System.out.println("5. View Staff");
            System.out.println("6. Manage Reservations");
            System.out.println("7. Start New Order"); // New option for starting an order
            System.out.println("8. Generate Sales Report"); // New option for generating sales report
            System.out.println("9. Quit");
            System.out.print("Choose an option: ");

            // Validate input to prevent InputMismatchException
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear any extra newline characters
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 9.");
                scanner.nextLine(); // Clear the invalid input
                continue;
            }

            switch (choice) {
                case 1 -> chooseCustomer();
                case 2 -> menu.showMenu();
                case 3 -> manageOrders();
                case 4 -> manageTables();
                case 5 -> staff.displayInfo();
                case 6 -> manageReservations();
                case 7 -> startOrder(); // Call method to start a new order
                case 8 -> generateSalesReport(); // Call method to generate sales report
                case 9 -> {
                    System.out.println("Exiting system...");
                    return;
                }
                default -> System.out.println("Invalid option. Please choose a number between 1 and 9.");
            }
        }
    }

    private static void chooseCustomer() {
        System.out.println("\nSelect a Customer:");
        System.out.println("1. " + customer1.getName());
        System.out.println("2. " + customer2.getName());
        System.out.print("Choose a customer: ");
        int customerChoice = scanner.nextInt();

        switch (customerChoice) {
            case 1 -> manageCustomer(customer1);
            case 2 -> manageCustomer(customer2);
            default -> System.out.println("Invalid selection.");
        }
    }

    private static void manageCustomer(Customer customer) {
        System.out.println("\nCustomer Information:");
        customer.displayInfo();
        System.out.println("Options: 1. Edit Info | 2. Back");
        int choice = scanner.nextInt();
        if (choice == 1) {
            System.out.print("Enter new phone: ");
            String phone = scanner.next();
            System.out.print("Enter new email: ");
            String email = scanner.next();
            System.out.print("Enter new address: ");
            String address = scanner.next();
            customer.updateContactInfo(phone, email, address);
            System.out.println("Customer updated.");
        }
    }

    private static void manageOrders() {
        System.out.println("\nOrder Management:");
        System.out.println("1. View Order Items");
        System.out.println("2. Add Item to Order");
        System.out.println("3. Remove Item from Order");
        System.out.println("4. Cancel Order");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> order.displayOrderItems();
            case 2 -> {
                System.out.println("\nAvailable Items:");
                menu.showMenu();
                System.out.print("Enter the ID of the item you want to add: ");
                int itemId = scanner.nextInt();
                menu.getItems().stream()
                    .filter(item -> item.getItemID() == itemId)
                    .findFirst()
                    .ifPresentOrElse(
                        order::addItem,
                        () -> System.out.println("Item not found.")
                    );
            }
            case 3 -> {
                System.out.print("Enter item ID to remove: ");
                int itemId = scanner.nextInt();
                menu.getItems().stream()
                    .filter(item -> item.getItemID() == itemId)
                    .findFirst()
                    .ifPresent(order::removeItem);
                System.out.println("Item removed.");
            }
            case 4 -> {
                order.cancelOrder();
                System.out.println("Order canceled.");
            }
            default -> System.out.println("Invalid option.");
        }
    }

    private static void manageTables() {
        System.out.println("\nTable Management:");
        System.out.println("Table ID: " + table.getTableID());
        System.out.println("Status: " + table.getStatus());
        System.out.println("Options: 1. Reserve | 2. Release | 3. Back");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> {
                table.reserveTable(customer1);
                System.out.println("Table reserved for " + customer1.getName());
            }
            case 2 -> {
                table.releaseTable();
                System.out.println("Table released.");
            }
            case 3 -> System.out.println("Returning to main menu...");
            default -> System.out.println("Invalid option.");
        }
    }

    private static void manageReservations() {
        System.out.println("\nReservation Management:");
        System.out.println("1. View Reservation");
        System.out.println("2. Update Reservation");
        System.out.println("3. Cancel Reservation");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> reservation.viewReservation();
            case 2 -> {
                System.out.print("Enter new date (YYYY-MM-DD): ");
                String date = scanner.next();
                System.out.print("Enter new time (HH:MM): ");
                String time = scanner.next();
                reservation.updateReservation(date, time);
                System.out.println("Reservation updated.");
            }
            case 3 -> {
                reservation.cancelReservation();
                System.out.println("Reservation canceled.");
            }
            default -> System.out.println("Invalid option.");
        }
    }

    // New method to start a new order and add items
    private static void startOrder() {
        Order newOrder = new Order(generateOrderID(), table.getTableID());

        System.out.println("\nStarting a new order. Here are the items you can add:");
        menu.showMenu();

        boolean addingItems = true;
        while (addingItems) {
            System.out.print("Enter item ID to add (or type 'done' to finish): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("done")) {
                addingItems = false;
            } else {
                try {
                    int itemId = Integer.parseInt(input);
                    Item item = findItemById(itemId);

                    if (item != null) {
                        newOrder.addItem(item);
                        System.out.println(item.getName() + " added to order.");
                    } else {
                        System.out.println("Item not found. Please enter a valid item ID.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Enter a number for the item ID.");
                }
            }
        }

        processPayment(newOrder);
    }

    // Helper method to find an item by ID
    private static Item findItemById(int itemId) {
        for (Item item : menu.getItems()) {
            if (item.getItemID() == itemId) {
                return item;
            }
        }
        return null;
    }

    // Method to process payment for the completed order
    private static void processPayment(Order order) {
        double totalAmount = 0;
        for (Item item : order.getItems()) {
            totalAmount += item.getPrice();
        }

        System.out.println("Total amount due: $" + totalAmount);
        System.out.print("Enter payment type (cash/card): ");
        String paymentType = scanner.nextLine();

        Payment payment = new Payment(generatePaymentID(), order.getOrderID(), totalAmount, paymentType);
        payment.processPayment();
        completedOrders.add(order);
        System.out.println("Order completed and payment processed.");
    }

    //  method to generate unique order IDs
    private static int generateOrderID() {
        return completedOrders.size() + 1001;
    }

    //  method to generate unique payment IDs
    private static int generatePaymentID() {
        return completedOrders.size() + 2001;
    }

    //  method to generate a sales report
    private static void generateSalesReport() {
        SalesReport salesReport = new SalesReport(completedOrders);
        salesReport.generateTotalSales();           // To display the total sales amount
        salesReport.generateItemSalesReport();      // To display a detailed item sales report
    }
}
