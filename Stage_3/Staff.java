/**
 * Bandera Cafe restaurant System
 * For cs 234
 * @auther Ayham Abbad
 * @auther Brenda Vanessa
 * @auther Andrew Boese
 */
/**
 * @param staffID The unique identifier for the staff.
 * @param role The role of the staff (e.g., barista, manager).
 * @param schedule The work schedule assigned to the staff member.
 * @return Staff details.
 */
/**
 * Represents a staff member of the cafe.
 * Contains staff details such as ID, role, contact information, and schedule.
 */
public class Staff extends Person {
    private int staffID;
    private String role;
    private Schedule schedule;
    private double hoursWorked;
    private double hourlyRate = 15.00;

    public Staff(int staffID, String name, String role, String phone, String email, Schedule schedule) {
        super(name, phone, email, "");  // Set default address as "N/A"
        this.staffID = staffID;
        this.role = role;
        this.schedule = schedule;
    }

    public void addHours(double hours) {
        this.hoursWorked += hours;
    }

    public double calculatePaycheck() {
        return hoursWorked * hourlyRate;
    }

    public int getStaffID() {
        return staffID;
    }

    public String getRole() {
        return role;
    }
}

