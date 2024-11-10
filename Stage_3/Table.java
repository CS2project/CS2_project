/**
 * Bandera Cafe restaurant System
 * For cs 234
 * @auther Ayham Abbad
 * @auther Brenda Vanessa
 * @auther Andrew Boese
 */
/**
 * @param tableID The unique identifier for the table.
 * @param capacity The seating capacity of the table.
 * @param isOccupied The current occupancy status of the table.
 * @return Table details.
 */
/**
 * Represents a table in the cafe.
 * Contains table details such as table ID, seating capacity, and availability.
 */
public class Table {
    private int tableID;
    private int capacity;
    private boolean isOccupied;
    private boolean isReserved;
    private Customer assignedCustomer;

    public Table(int tableID, int capacity) {
        this.tableID = tableID;
        this.capacity = capacity;
        this.isOccupied = false;
        this.isReserved = false;
    }

    public int getTableID() {
        return tableID;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean checkAvailability() {
        return !isOccupied && !isReserved;
    }

    public void occupyTable(Customer customer) {
        if (checkAvailability()) {
            isOccupied = true;
            assignedCustomer = customer;
        } else {
            System.out.println("Table is not available to occupy.");
        }
    }

    public void releaseTable() {
        isOccupied = false;
        isReserved= false;
        assignedCustomer = null;
    }

    public void reserveTable(Customer customer) {
        if (!isReserved) {
            isReserved = true;
            assignedCustomer = customer;
        } else {
            System.out.println("Table is already reserved.");
        }
    }

    public void unreserveTable() {
        isReserved = false;
        assignedCustomer = null;
    }

    public String getStatus() {
        if (isOccupied) return "Occupied";
        if (isReserved) return "Reserved";
        return "Available";
    }
}
