package jpa.base;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
@Entity
@Table(schema = "basic", name = "string_prop")
public class StringProperty implements Property<String> {

    @Id
    private Long id;
    private String key;
    private String value;

    @Override
    public String getKey() {

        return key;
    }

    @Override
    public String getValue() {

        return value;
    }


    public void setValue(String value) {

        this.value = value;
    }

    public void setKey(String key) {

        this.key = key;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }
}
