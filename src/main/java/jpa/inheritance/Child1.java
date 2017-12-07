package jpa.inheritance;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
@Entity
@Table(schema = "inheritance", name = "child_1")
public class Child1 extends Parent {

    private String value;

    public String getValue() {

        return value;
    }

    public void setValue(String value) {

        this.value = value;
    }
}
