package jpa.fetch;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
@Entity(name = "department_fetch")
@Table(schema = "fetch", name = "department")
@NamedEntityGraph(name = "department.persons",
        attributeNodes = @NamedAttributeNode("persons"))
public class Department {

    @Id
    private Long id;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    @BatchSize(size = 3)
    private List<Person> persons = new ArrayList<>();

    public void addPerson(Person person) {

        person.setDepartment(this);
        this.persons.add(person);
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public List<Person> getPersons() {

        return persons;
    }

    public void setPersons(List<Person> persons) {

        this.persons = persons;
    }
}
