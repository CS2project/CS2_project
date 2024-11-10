
/**
 * Handles payment processing for orders.
 * Contains methods for calculating and processing payments.
 * 
 * @param paymentID The unique identifier for the payment.
 * @param orderID The identifier of the order being paid for.
 * @param amount The amount of the payment.
 * @param paymentType The type of payment used (e.g., cash, credit).
 * @return The processed payment details.
 */
/**
 * Represents a payment made for an order at the cafe.
 */
public class Payment {
    private int paymentID;
    private int orderID;
    private double amount;
    private String paymentType;

    public Payment(int paymentID, int orderID, double amount, String paymentType) {
        this.paymentID = paymentID;
        this.orderID = orderID;
        this.amount = amount;
        this.paymentType = paymentType;
    }

    public void processPayment() {
        System.out.println("Payment processed: $" + amount + " for order ID: " + orderID + " via " + paymentType);
    }
}

