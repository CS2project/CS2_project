package backend;

/**
 * Bandera Cafe restaurant System
 * For cs 234
 * @auther Ayham Abbad
 * @auther Brenda Vanessa
 * @auther Andrew Boese
 */
/**
 * @param itemID The unique identifier for the item.
 * @param name The name of the menu item.
 * @param price The price of the item.
 * @param category The category or type of the item (e.g., beverage, food).
 * @param isAvailable Availability status of the item.
 */
/**
 * Represents an item available on the cafe's menu.
 * Contains item details such as item ID, name, price, category, and availability.
 */
public class Item {
    private int itemID;
    private String name;
    private double price;
    private String category;
    private boolean isAvailable;

    public Item(int itemID, String name, double price, String category, boolean isAvailable) {
        this.itemID = itemID;
        this.name = name;
        this.price = price;
        this.category = category;
        this.isAvailable = isAvailable;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getItemID() {
        return itemID;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailability(boolean available) {
        isAvailable = available;
    }

    public String getCategory() {
        return category;
    }

    public boolean isIsAvailable() {
        return isAvailable;
    }
    
}
