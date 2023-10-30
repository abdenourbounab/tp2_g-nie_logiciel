package theatricalplays;

public class Customer {
    private String name;
    private int customerNumber; 
    private int loyaltyPoints;

    public Customer(String name, int customerNumber, int loyaltyPoints) { 
        this.name = name;
        this.customerNumber = customerNumber;
        this.loyaltyPoints = loyaltyPoints;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for customerNumber
    public int getCustomerNumber() { 
        return customerNumber;
    }

    // Setter for customerNumber
    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    // Getter for loyaltyPoints
    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    // Setter for loyaltyPoints
    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }
}
