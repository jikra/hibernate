import jpa.base.Person;
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
}
