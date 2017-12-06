import jpa.association.Customer;
import jpa.base.Person;
import jpa.base.PersonInfo;
import jpa.base.PropHolder;
import jpa.base.StringProperty;
import jpa.fetch.Department;
import jpa.identifier.PersonKey;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

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

    @Test
    public void test_embededId() {

        PersonKey personKey = new PersonKey();
        personKey.setKey("JIK");
        personKey.setLang("CZ");

        jpa.identifier.Person person = new jpa.identifier.Person();
        person.setName("jikra");
        person.setPersonKey(personKey);

        doInJpa(entityManager -> entityManager.persist(person));
    }

    @Test
    public void test_association() {
        jpa.association.Department dep1 = new jpa.association.Department();
        jpa.association.Department dep2 = new jpa.association.Department();

        Customer customer1 = new Customer();
        Customer customer2 = new Customer();

        dep1.addCustomer(customer1);
        dep1.addCustomer(customer2);

        dep2.addCustomer(customer1);

        doInJpa(entityManager -> {
            entityManager.persist(customer1);
            entityManager.persist(customer2);
            entityManager.persist(dep1);
            entityManager.persist(dep2);

            entityManager.flush();
            System.out.println("-------------");
            dep1.removeCustomer(customer1);
        });

    }


    @Test
    public void test_fetch() {

        init();
        System.out.println("-------------------");
        doInJpa(entityManager -> entityManager.find(jpa.fetch.Department.class, 11L));
    }

    @Test
    public void test_fetchGraph() {

        init();
        System.out.println("-------------------");
        doInJpa(entityManager -> entityManager.find(Department.class, 11L,
                Collections.singletonMap("javax.persistence.fetchgraph",
                        entityManager.getEntityGraph("department.persons"))));
    }

    @Test
    public void test_fetchBatch() {

        init();
        System.out.println("----------------");
        doInJpa(entityManager -> {
            List<Department> resultList = entityManager.createQuery("select d from department_fetch d")
                    .getResultList();
            for (Department department : resultList) {
                department.getPersons().size();
            }
        });
    }

    @Test
    public void test_fetchQuerySize() {

        init();
        System.out.println("-------------------");
        doInJpa(entityManager -> {
            Department department = entityManager.find(Department.class, 11L);
            department.getPersons().size();
        });
    }

    @Test
    public void test_fetchQueryDynamic() {

        init();
        System.out.println("-------------------");
        doInJpa(entityManager -> entityManager
                .createQuery("select d from department_fetch d " +
                        "left join fetch d.persons where d.id=11")
                .getSingleResult());
    }

    @Test
    public void test_fetchQuery() {

        init();
        System.out.println("-------------------");
        doInJpa(entityManager -> entityManager
                .createQuery("select d from department_fetch d where d.id=11")
                .getSingleResult());
    }

    private void init() {

        jpa.fetch.Department department = new jpa.fetch.Department();
        department.setId(11L);

        jpa.fetch.Department department1 = new jpa.fetch.Department();
        department1.setId(12L);

        jpa.fetch.Person person = new jpa.fetch.Person();
        person.setId(1L);

        jpa.fetch.Person person1 = new jpa.fetch.Person();
        person1.setId(2L);

        jpa.fetch.Person person2 = new jpa.fetch.Person();
        person2.setId(3L);

        jpa.fetch.Person person3 = new jpa.fetch.Person();
        person3.setId(4L);

        department.addPerson(person);
        department.addPerson(person1);
        department1.addPerson(person2);
        department1.addPerson(person3);

        doInJpa(entityManager -> entityManager.persist(department));
        doInJpa(entityManager -> entityManager.persist(department1));
        doInJpa(entityManager -> entityManager.merge(person));
        doInJpa(entityManager -> entityManager.merge(person1));
        doInJpa(entityManager -> entityManager.merge(person2));
        doInJpa(entityManager -> entityManager.merge(person3));
    }
}
