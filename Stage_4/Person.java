package backend;

/**
 * Bandera Cafe restaurant System
 * For cs 234
 * @auther Ayham Abbad
 * @auther Brenda Vanessa
 * @auther Andrew Boese
 */
/**
 * @param name The name of the person.
 * @param phone The phone number of the person.
 * @param email The email address of the person.
 * @param address The physical address of the person.
 * @return Details about the person.
 *
 */
/**
 * A base class representing a person with common attributes.
 * Extended by Customer and Staff classes.
 */
public class Person {
    private String name;
    private String phone;
    private String email;
    private String address;

    public Person(String name, String phone, String email, String address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void updateContactInfo(String phone, String email, String address) {
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Address: " + address);
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }
    
}
