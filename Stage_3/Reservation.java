/**
 * Bandera Cafe restaurant System
 * For cs 234
 * @auther Ayham Abbad
 * @auther Brenda Vanessa
 * @auther Andrew Boese
 */
/**
 * @param reservationID Unique identifier for the reservation.
 * @param customerID ID of the customer making the reservation.
 * @param tableID ID of the reserved table.
 * @param date Date of the reservation.
 * @param time Time of the reservation.
 * @return Reservation details.
 */
/**
 * Manages table reservations at the cafe with details such as reservation ID, customer, table ID, date, and time.
 */
public class Reservation {
    private int reservationID;
    private int customerID;
    private int tableID;
    private String date;
    private String time;

    public Reservation(int reservationID, int customerID, int tableID, String date, String time) {
        this.reservationID = reservationID;
        this.customerID = customerID;
        this.tableID = tableID;
        this.date = date;
        this.time = time;
    }

    public void makeReservation() {
        System.out.println("Reservation made with ID: " + reservationID + " for customer ID: " + customerID);
        System.out.println("Table ID: " + tableID + " on " + date + " at " + time);
    }

    public void viewReservation() {
        System.out.println("Reservation ID: " + reservationID);
        System.out.println("Customer ID: " + customerID);
        System.out.println("Table ID: " + tableID);
        System.out.println("Date: " + date + " Time: " + time);
    }

    public void updateReservation(String newDate, String newTime) {
        this.date = newDate;
        this.time = newTime;
        System.out.println("Reservation updated to date: " + date + " and time: " + time);
    }

    public void cancelReservation() {
        System.out.println("Reservation canceled with ID: " + reservationID);
    }
}
