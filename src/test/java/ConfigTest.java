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


    public void doInJpa(Consumer<EntityManager> action) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        action.accept(entityManager);

        transaction.commit();
        entityManager.close();

    }

    public void doInJpaDelay(Consumer<EntityManager> action, long milis) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        action.accept(entityManager);

        try {
            Thread.sleep(milis);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        transaction.commit();
        entityManager.close();

    }
}
