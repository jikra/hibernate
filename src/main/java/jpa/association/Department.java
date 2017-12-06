package jpa.association;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
@Entity
@Table(schema = "association", name = "department")
public class Department {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(schema = "association")
    private List<Customer> customers = new ArrayList<>();

    public void addCustomer(Customer customer) {

        customers.add(customer);
        customer.getDepartments().add(this);
    }

    public void removeCustomer(Customer customer) {

        customers.remove(customer);
        customer.getDepartments().remove(this);
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public List<Customer> getCustomers() {

        return customers;
    }

    public void setCustomers(List<Customer> customers) {

        this.customers = customers;
    }
}
