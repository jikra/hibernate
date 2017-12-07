package jpa.context;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
@Entity
@Table(schema = "context", name = "data_ctx")
public class DataCtx {

    @Id
    private Long id;

    @NaturalId
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
