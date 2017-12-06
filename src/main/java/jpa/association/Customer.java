package jpa.association;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
@Entity
@Table(schema = "association", name = "customer")
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(mappedBy = "customers")
    private List<Department> departments = new ArrayList<>();

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public List<Department> getDepartments() {

        return departments;
    }

    public void setDepartments(List<Department> departments) {

        this.departments = departments;
    }
}
