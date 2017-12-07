package jpa.inheritance;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
@Entity
@Table(schema = "inheritance", name = "child_2")
public class Child2 extends Parent {

    private String key;

    public String getKey() {

        return key;
    }

    public void setKey(String key) {

        this.key = key;
    }
}
