package backend;

/**
 * Bandera Cafe restaurant System
 * For cs 234
 * @auther Ayham Abbad
 * @auther Brenda Vanessa
 * @auther Andrew Boese
 */
/**
 * @param orderID The unique identifier for the order.
 * @param tableID The identifier for the table where the order was placed.
 * @method addItem Adds an item to the order.
 * @param item The item being added to the order.
 * @return Information about the items in the order.
 */
/**
 * Represents a customer order in the cafe.
 * Contains order details such as items ordered, quantities, and associated table.
 */
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderID;
    private List<Item> items;  // List of items in the order
    private int tableID;

    public Order(int orderID, int tableID) {
        this.orderID = orderID;
        this.tableID = tableID;
        this.items = new ArrayList<>();  // Initialize items list
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void cancelOrder() {
        items.clear();
    }

    public void displayOrderItems() {
        System.out.println("Order ID: " + orderID);
        for (Item item : items) {
            System.out.println(item.getName() + " - $" + item.getPrice());
        }
    }

    public int getOrderID() {
        return orderID;
    }

    // New method to retrieve the list of items in the order
    public List<Item> getItems() {
        return items;
    }

    public String getTableID() {
        return (tableID+"");
    }
    
    
}
