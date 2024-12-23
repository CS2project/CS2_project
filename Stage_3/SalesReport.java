/**
 * Bandera Cafe restaurant System
 * For cs 234
 * @auther Ayham Abbad
 * @auther Brenda Vanessa
 * @auther Andrew Boese
 */
/**
 * @param completedOrders List of completed orders used for generating the report.
 * @method generateTotalSales Calculates total revenue from completed orders.
 * @return Total revenue generated.
 */
/**
 * Generates sales reports based on completed orders.
 * Contains methods to compile and display performance and revenue metrics.
 */
import java.util.List;

public class SalesReport {
	private List<Order> completedOrders;

	public SalesReport(List<Order> completedOrders) {
		this.completedOrders = completedOrders;
	}

	// Generates a report with the total sales amount from all completed orders
	public void generateTotalSales() {
		double totalSales = 0;
		for (Order order : completedOrders) {
			double orderTotal = 0;
			for (Item item : order.getItems()) {
				orderTotal += item.getPrice(); // Adds up the price of each item in the order
			}
			totalSales += orderTotal; // Adds each orderb s total to the total sales
		}
		System.out.println("Total Sales: $" + totalSales);
	}

	// Lists all items in each completed order with their prices
	public void generateItemSalesReport() {
		System.out.println("Item Sales Report:");
		for (Order order : completedOrders) {
			System.out.println("Order ID: " + order.getOrderID());
			for (Item item : order.getItems()) {
				System.out.println(" - " + item.getName() + " - $" + item.getPrice());
			}
		}
	}
}