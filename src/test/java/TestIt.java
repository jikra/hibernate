import jpa.base.Person;
import jpa.base.PersonInfo;
import jpa.base.PropHolder;
import jpa.base.StringProperty;
import org.junit.Test;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
public class TestIt extends ConfigTest {

    @Test
    public void test_createEntity() {

        Person person = new Person();
        person.setId(1L);
        person.setName("name");

        doInJpa(entityManager -> entityManager.persist(person));
    }

    @Test
    public void test_polym() {

        StringProperty stringProperty = new StringProperty();
        stringProperty.setId(1L);
        stringProperty.setKey("OS");
        stringProperty.setValue("MAC");

        PropHolder propHolder = new PropHolder();
        propHolder.setId(1L);
        propHolder.setProperty(stringProperty);

        doInJpa(entityManager -> entityManager.persist(stringProperty));
        doInJpa(entityManager -> entityManager.persist(propHolder));
    }

    @Test
    public void test_subselect() {

        Person person = new Person();
        person.setId(11L);
        person.setName("jikra");
        person.setAge(30);
        person.setEmail("cizek@cizek.com");

        doInJpa(entityManager -> entityManager.persist(person));
        doInJpa(entityManager -> {
            Person loadedPerson = entityManager.find(Person.class, 11L);
            System.out.println(loadedPerson);
        });

        doInJpa(entityManager -> {
            PersonInfo personInfo = entityManager.find(PersonInfo.class, 11L);
            System.out.println(personInfo);
        });
    }

}
