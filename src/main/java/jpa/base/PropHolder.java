package jpa.base;

import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

import javax.persistence.*;

@AnyMetaDef(name = "propertyMeta", metaType = "string", idType = "long",
        metaValues = {
                @MetaValue(value = "sprop", targetEntity = StringProperty.class),
                @MetaValue(value = "lprop", targetEntity = LongProperty.class)
        }
)
@Entity
@Table(schema = "basic", name = "prop_holder")
public class PropHolder {

    @Id
    private Long id;

    @Any(
            metaDef = "propertyMeta",
            metaColumn = @Column(name = "property_type")
    )
    @JoinColumn(name = "property_id")
    private Property property;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Property getProperty() {

        return property;
    }

    public void setProperty(Property property) {

        this.property = property;
    }
}
