package jpa.cache1;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
@Entity
@Table(schema = "cache", name = "person_cache")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PersonCache {

    @Id
    private Long id;

    @ElementCollection
    @CollectionTable(schema = "cache", name = "data1",
        joinColumns = @JoinColumn(name = "data_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<String> data = new ArrayList<>();


    @OneToMany
    @JoinTable(schema = "cache")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<ChildEntity> childs = new ArrayList<>();

    public List<ChildEntity> getChilds() {

        return childs;
    }

    public void setChilds(List<ChildEntity> childs) {

        this.childs = childs;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public List<String> getData() {

        return data;
    }

    public void setData(List<String> data) {

        this.data = data;
    }
}
