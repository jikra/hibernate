package jpa.base;

import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
@Entity
@Subselect("Select p.id as id, p.name as name, p.email as email from basic.Person p")
@Synchronize({"Person"})
public class PersonInfo {

    @Id
    private Long id;

    private String name;

    private String email;

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

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    @Override
    public String toString() {

        return "PersonInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
