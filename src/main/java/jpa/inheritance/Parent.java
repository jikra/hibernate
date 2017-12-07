package jpa.inheritance;

import javax.persistence.*;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
@Entity
@Table(schema = "inheritance", name = "parent")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Parent {

    @Id
    private Long id;

    private String name;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }
}
