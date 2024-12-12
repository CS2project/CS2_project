package backend;

/**
 * Bandera Cafe restaurant System
 * For cs 234
 * @auther Ayham Abbad
 * @auther Brenda Vanessa
 * @auther Andrew Boese
 */
/**
 * @param staffID The unique identifier for the staff.
 * @param dayOfWeek The day of the week for the schedule.
 * @param shiftType The type of shift (e.g., morning, evening).
 * @return Schedule details.
 */
/**
 * Manages the schedule for staff or table reservations.
 * Contains schedule information such as day, time, and assigned staff or table.
 */
public class Schedule {
    private int staffID;
    private String dayOfWeek;
    private String shiftType;

    public Schedule(int staffID, String dayOfWeek, String shiftType) {
        this.staffID = staffID;
        this.dayOfWeek = dayOfWeek;
        this.shiftType = shiftType;
    }

    public int getStaffID() {
        return staffID;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }
}
