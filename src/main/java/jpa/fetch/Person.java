package jpa.fetch;

import javax.persistence.*;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
@Entity(name = "person_fetch")
@Table(schema = "fetch", name = "person")
public class Person {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Department getDepartment() {

        return department;
    }

    public void setDepartment(Department department) {

        this.department = department;
    }


}
