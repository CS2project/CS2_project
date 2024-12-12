package backend;


/**
 * Represents the cafe's menu, containing a list of available items.
 * Provides methods for adding, removing, and listing items on the menu.
 *
 * @method addItem Adds an item to the menu.
 * @param item The item to add to the menu.
 * @method getItems Returns a list of items available on the menu.
 * @return List of items in the menu.
 */
/**
 * Represents the cafe's menu with available items.
 */
import java.util.ArrayList;
import java.util.List;
public class Menu {
    private List<Item> items;

    public Menu() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public void showMenu() {
        System.out.println("\n--- Cafe Menu ---");
        for (Item item : items) {
            System.out.println(item.getItemID() + ". " + item.getName() + " - $" + item.getPrice());
        }
    }
    public void loadDefaultItems() {
        addItem(new Item(1, "Espresso", 3.0, "Beverage", true));
        addItem(new Item(2, "Cappuccino", 4.5, "Beverage", true));
        addItem(new Item(3, "Latte", 4.0, "Beverage", true));
        addItem(new Item(4, "Croissant", 3.0, "Snack", true));
        addItem(new Item(5, "Blueberry Muffin", 2.5, "Snack", true));
    }

}
