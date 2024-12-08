package backend;

/**
 * Bandera Cafe restaurant System
 * For cs 234
 * @auther Ayham Abbad
 * @auther Brenda Vanessa
 * @auther Andrew Boese
 */
/**
 * Represents a customer at the cafe.
 * Holds customer details such as ID, name, contact information, and address.
 *
 * @param customerID The unique identifier for the customer.
 * @param name The name of the customer.
 * @param phone The contact phone number of the customer.
 * @param email The email address of the customer.
 * @param address The physical address of the customer.
 */
/**
 * Represents a customer of the cafe.
 * Contains customer details such as ID, name, contact information, and address.
 */
public class Customer extends Person {
    private int customerID;

    public Customer(int customerID, String name, String phone, String email, String address) {
        super(name, phone, email, address);
        this.customerID = customerID;
    }

    public void makePayment(Order order) {
        System.out.println("Processing payment for order ID: " + order.getOrderID());
    }

    public int getCustomerID() {
        return customerID;
    }
    
}
