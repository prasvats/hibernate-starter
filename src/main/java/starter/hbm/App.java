package starter.hbm;

import com.querydsl.jpa.impl.JPAQuery;
import org.jooby.Jooby;
import org.jooby.hbm.Hbm;
import org.jooby.jdbc.Jdbc;
import org.jooby.json.Jackson;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

import static starter.hbm.QPet.pet;

/**
 * Hibernate ORM Starter project.
 */
public class App extends Jooby {

  {
    /** JSON: */
    use(new Jackson());

    /** Jdbc: */
    use(new Jdbc());

    /**
     * Hibernate:
     */
    use(new Hbm()
        .classes(Pet.class)
    );

    /**
     * Insert some data on startup:
     */
    onStarted(() -> {
      EntityManagerFactory factory = require(EntityManagerFactory.class);

      EntityManager em = factory.createEntityManager();

      EntityTransaction trx = em.getTransaction();
      trx.begin();
      em.persist(new Pet("Lala"));
      em.persist(new Pet("Mandy"));
      em.persist(new Pet("Fufy"));
      em.persist(new Pet("Dina"));
      trx.commit();

      em.close();
    });

    /** Open session in view filter: */
    use("*", Hbm.openSessionInView());

    /**
     * Find all via query-dsl:
     */
    get("/pets", req -> {
      EntityManager em = require(EntityManager.class);
      List<Pet> pets = new JPAQuery<>(em)
          .select(pet)
          .from(pet)
          .fetch();
      return pets;
    });

    /**
     * Find by id via entity manager:
     */
    get("/pets/{id:\\d+}", req -> {
      int id = req.param("id").intValue();
      EntityManager em = require(EntityManager.class);
      return em.find(Pet.class, id);
    });

    /**
     * Find by name via query-dsl:
     */
    get("/pets/:name", req -> {
      EntityManager em = require(EntityManager.class);
      String name = req.param("name").value();
      List<Pet> pets = new JPAQuery<>(em)
          .select(pet)
          .from(pet)
          .where(pet.name.likeIgnoreCase(name))
          .fetch();
      return pets;
    });
  }

  public static void main(final String[] args) {
    run(App::new, args);
  }

}
