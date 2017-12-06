import org.junit.Before;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.function.Consumer;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
public class ConfigTest {

    protected EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PERSISTENCE");
    protected EntityManager entityManager;

    public void doInJpa(Consumer<EntityManager> action) {
        entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        action.accept(entityManager);

        transaction.commit();
        entityManager.close();

    }
}
