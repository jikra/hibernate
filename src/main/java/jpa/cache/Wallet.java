package jpa.cache;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
@Entity
@Table(schema = "cache", name = "wallet")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Wallet {

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

    @Override
    public String toString() {

        return "Wallet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
