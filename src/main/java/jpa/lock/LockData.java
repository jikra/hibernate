package jpa.lock;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLock;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
@Entity
@Table(schema = "lock", name = "lock_data")
@OptimisticLocking(type = OptimisticLockType.ALL)
@DynamicUpdate
public class LockData {

    @Id
    private Long id;

//    @OptimisticLock(excluded = true)
    private String key;

    private String value;

//    @Version
//    private Long version;
//
//    public Long getVersion() {
//
//        return version;
//    }
//
//    public void setVersion(Long version) {
//
//        this.version = version;
//    }

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
