package jpa.cache;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
@Entity
@Table(schema = "cache", name = "personc")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "non-lazy")
public class PersonC {

    @Id
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(schema = "cache")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<OrderC> orderCList = new ArrayList<>();

    @ElementCollection
    @CollectionTable(schema = "cache", name="data", joinColumns=@JoinColumn(name="data_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<String> data = new HashSet<>();

    public Set<String> getData() {

        return data;
    }

    public void setData(Set<String> data) {

        this.data = data;
    }

    public List<OrderC> getOrderCList() {

        return orderCList;
    }

    public void setOrderCList(List<OrderC> orderCList) {

        this.orderCList = orderCList;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }
}
