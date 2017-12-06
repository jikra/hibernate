package jpa.base;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
@Entity
@Table(schema = "basic", name = "long_prop")
public class LongProperty implements Property<Long> {

    @Id
    private Long id;
    private String key;
    private Long value;

    @Override
    public String getKey() {

        return key;
    }

    @Override
    public Long getValue() {

        return value;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public void setKey(String key) {

        this.key = key;
    }

    public void setValue(Long value) {

        this.value = value;
    }
}
