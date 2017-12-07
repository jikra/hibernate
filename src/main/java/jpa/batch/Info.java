package jpa.batch;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
@Entity
@Table(schema = "batch", name = "info")
public class Info {

    @Id
    @SequenceGenerator(
            sequenceName = "seq_ge_info",
            schema = "batch",
            name = "sequenceGenerator",
            initialValue = 1,
            allocationSize = 50

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequenceGenerator")
    private Long id;

    @Version
    private Long version;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }
}
