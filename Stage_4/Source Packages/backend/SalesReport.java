package backend;

import java.util.List;

public class SalesReport {
    private List<Order> completedOrders;

    public SalesReport(List<Order> completedOrders) {
        this.completedOrders = completedOrders;
    }

    // Generates the total sales amount from all completed orders
    public String generateTotalSales() {
        double totalSales = 0;

        for (Order order : completedOrders) {
            double orderTotal = 0;
            for (Item item : order.getItems()) {
                orderTotal += item.getPrice(); // Sum item prices for the order
            }
            totalSales += orderTotal;
        }

        return "Total Sales: $" + String.format("%.2f", totalSales);
    }

    // Generates a detailed sales report for each order and item
    public String generateItemSalesReport() {
        StringBuilder report = new StringBuilder();
        int orderNumber = 1;

        for (Order order : completedOrders) {
            report.append("Order #").append(orderNumber).append("\n");
            for (Item item : order.getItems()) {
                report.append("- ").append(item.getName())
                        .append(": $").append(String.format("%.2f", item.getPrice()))
                        .append("\n");
            }
            orderNumber++;
        }

        return report.toString();
    }
}
