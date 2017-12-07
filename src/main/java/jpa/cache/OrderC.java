package jpa.cache;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
@Entity
@Table(schema = "cache", name = "orderc")
public class OrderC {

    @Id
    private Long id;

    private String key;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getKey() {

        return key;
    }

    public void setKey(String key) {

        this.key = key;
    }
}
