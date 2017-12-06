package jpa.identifier;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
@Entity(name = "personI")
@Table(schema = "identifier", name = "person")
public class Person {

    @EmbeddedId
    private PersonKey personKey;

    private String name;

    public PersonKey getPersonKey() {

        return personKey;
    }

    public void setPersonKey(PersonKey personKey) {

        this.personKey = personKey;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }
}
