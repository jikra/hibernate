import jpa.association.Customer;
import jpa.base.Person;
import jpa.base.PersonInfo;
import jpa.base.PropHolder;
import jpa.base.StringProperty;
import jpa.batch.Info;
import jpa.cache.Order;
import jpa.cache.OrderC;
import jpa.cache.PersonC;
import jpa.cache.Wallet;
import jpa.context.Author;
import jpa.context.Book;
import jpa.context.DataCtx;
import jpa.fetch.Department;
import jpa.flush.Flash;
import jpa.flush.ReverseFlash;
import jpa.identifier.PersonKey;
import jpa.inheritance.Child1;
import jpa.inheritance.Child2;
import jpa.inheritance.Parent;
import jpa.lock.LockData;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.internal.CacheImpl;
import org.junit.Test;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void test_cacheCol() {

        OrderC orderC = new OrderC();
        orderC.setId(1L);
        orderC.setKey("key");

        doInJpa(entityManager -> entityManager.persist(orderC));

        PersonC personC = new PersonC();
        personC.setId(1L);

        personC.getData().add("jikra1");
        personC.getData().add("jikra2");
        personC.getData().add("jikra3");
        personC.getOrderCList().add(orderC);

        doInJpa(entityManager -> entityManager.persist(personC));

        System.out.println("1");
        doInJpa(entityManager -> {
            PersonC personC1 = entityManager.find(PersonC.class, 1L);
            personC1.getOrderCList().size();
            personC1.getData().size();
        });

        System.out.println("2");
        doInJpa(entityManager -> {
            PersonC personC1 = entityManager.find(PersonC.class, 1L);
            personC1.getOrderCList().size();
            personC1.getData().size();
        });
    }

    @Test
    public void test_cache() {

        Order order = new Order();
        order.setId(1L);
        order.setCustomerName("jikra");

        Order order1 = new Order();
        order1.setId(2L);
        order1.setCustomerName("jikra1");

        Wallet wallet = new Wallet();
        wallet.setId(1L);
        wallet.setName("moje wallet");

        doInJpa(entityManager -> entityManager.persist(wallet));
        doInJpa(entityManager -> entityManager.persist(order));
        doInJpa(entityManager -> entityManager.persist(order1));

        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE);
        hints.put("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);

        System.out.println("FIND ORDER");
        doInJpa(entityManager -> {
            entityManager.find(Order.class, 1L, hints);
        });
        doInJpa(entityManager -> entityManager.find(Order.class, 1L));
        doInJpa(entityManager -> entityManager.find(Order.class, 1L));
        System.out.println("_FIND ORDER");

        doInJpa(entityManager -> entityManager.find(Order.class, 2L));
        doInJpa(entityManager -> entityManager.find(Wallet.class, 1L));

        doInJpa(entityManager -> {
            Order loadedOrder = entityManager.find(Order.class, 1L);
            loadedOrder.setCustomerName("new name");
        });

        doInJpa(entityManager -> entityManager.find(Order.class, 1L));
        doInJpa(entityManager -> {
            ((CacheImpl) entityManager.getEntityManagerFactory().getCache()).evictEntityRegion("jpa.cache.Order");
            entityManager.find(Order.class, 1L);
            entityManager.find(Wallet.class, 1L);
        });

        System.out.println("Query");
        doInJpa(entityManager -> {
            System.out.println(entityManager
                    .createQuery("select w from Wallet w WHERE w.id=1")
                    .setHint("org.hibernate.cacheable", "true")
                    .getSingleResult());
        });

        doInJpa(entityManager -> {
            Wallet wallet1 = entityManager.find(Wallet.class, 1L);
            wallet.setName("nova jikra");
        });

        doInJpa(entityManager -> {
            System.out.println(entityManager
                    .createQuery("select w from Wallet w WHERE w.id=1")
                    .setHint("org.hibernate.cacheable", "true")
                    .getSingleResult());
        });
    }

    @Test
    public void test_batch() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //        Session session = entityManager.unwrap(Session.class);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        int batchSize = 5;

        for (int i = 10; i > 1; i--) {
            Info info = new Info();
            entityManager.persist(info);

            if (i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }

            System.out.println("end");
        }

        transaction.commit();
        entityManager.close();
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

    @Test
    public void test_naturalId() {

        DataCtx dataCtx = new DataCtx();
        dataCtx.setId(11L);
        dataCtx.setName("jikra");

        doInJpa(entityManager -> entityManager.persist(dataCtx));

        doInJpa(entityManager -> {
            DataCtx reference = entityManager.getReference(DataCtx.class, 11L);
            System.out.println("");

        });

        doInJpa(entityManager -> {
            Session session = entityManager.unwrap(Session.class);
            session
                    .bySimpleNaturalId(DataCtx.class)
                    .load("jikra");
        });
    }

    @Test
    public void test_cascade() {

        Book book = new Book();
        book.setId(11L);
        book.setTitle("kniha");

        Author author = new Author();
        author.setId(12L);
        author.addBook(book);

        doInJpa(entityManager -> entityManager.persist(author));
    }

    @Test
    public void test_ctx() {

        DataCtx dataCtx = new DataCtx();
        dataCtx.setId(11L);
        dataCtx.setName("jikra");

        doInJpa(entityManager -> {
            entityManager.persist(dataCtx);
            dataCtx.setName("nova jikra");
        });

        dataCtx.setName("uplne nova jikra");
        doInJpa(entityManager -> {
            DataCtx merge = entityManager.merge(dataCtx);
        });
    }

    @Test
    public void test_flush() {

        Flash flash = new Flash();
        flash.setId(11L);
        flash.setName("I'am flash");

        ReverseFlash reverseFlash = new ReverseFlash();
        reverseFlash.setId(11L);
        reverseFlash.setName("I'am flash too");

        doInJpa(entityManager -> {

            Session session = entityManager.unwrap(Session.class);
            session.setHibernateFlushMode(FlushMode.ALWAYS);

            entityManager.persist(flash);
            System.out.println("persist flash");

            entityManager.find(Flash.class, 1L);
            System.out.println("find flash");

            entityManager.createQuery("select f from Flash f").getResultList();
            System.out.println("JPQL flash");

            entityManager.persist(reverseFlash);
            System.out.println("persist reverse flash");
        });
    }

    @Test
    public void test_inheritanceJoined() {

        Child2 child2 = new Child2();
        child2.setId(11L);
        child2.setName("jikra");
        child2.setKey("key");

        Child1 child1 = new Child1();
        child1.setId(12L);
        child1.setValue("value");
        child1.setName("chidl2");

        doInJpa(entityManager -> entityManager.persist(child1));
        doInJpa(entityManager -> entityManager.persist(child2));

        doInJpa(entityManager -> {
            Parent parent = entityManager.find(Parent.class, 12L);
            System.out.println("");
        });
    }

    @Test
    public void test_lock() {

        LockData lockData = new LockData();
        lockData.setId(11L);
        lockData.setKey("key");

        doInJpa(entityManager -> entityManager.persist(lockData));

        Thread thread1 = new Thread(() ->
                doInJpaDelay(entityManager -> {
                    LockData loadedData = entityManager.find(LockData.class, 11L);
                    loadedData.setKey("key1");
                }, 2000)
        );

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            doInJpa(entityManager -> {
                LockData loadedData = entityManager.find(LockData.class, 11L);
                loadedData.setKey("key2");
            });
        });

        thread1.start();
        thread2.start();

        try {
            Thread.sleep(10000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void lock_pesim() {

        LockData lockData = new LockData();
        lockData.setId(11L);
        lockData.setKey("key");

        doInJpa(entityManager -> entityManager.persist(lockData));

        Thread thread1 = new Thread(() -> {
            doInJpaDelay(entityManager -> {
                LockData lockData1 = entityManager.find(LockData.class, 11L, LockModeType.PESSIMISTIC_READ);
                lockData1.setKey("key1");
            }, 2000);
        });

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            doInJpa(entityManager -> {
                LockData lockData1 = entityManager.find(LockData.class, 11L, LockModeType.PESSIMISTIC_WRITE);
                lockData1.setKey("key2");
            });
        });

        thread1.start();
        thread2.start();

        try {
            Thread.sleep(10000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_cache1() {

        Order order = new Order();
        order.setId(11L);
        order.setCustomerName("jikra");

        doInJpa(entityManager -> entityManager.persist(order));

        doInJpa(entityManager -> entityManager.find(jpa.cache1.Order.class, 11L));
    }
}















