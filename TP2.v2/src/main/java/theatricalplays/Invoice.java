package theatricalplays;

import java.util.List;

public class Invoice {

  private Customer customer;
  public List<Performance> performances;

  public Invoice(Customer customer, List<Performance> performances) {
    this.customer = customer;
    this.performances = performances;
  }

  public Customer getCustomer() {
    return customer;
  }
  
}
