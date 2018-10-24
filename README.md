
# Google Jib Jooby Hibernate Starter


## Quick Preview

This project contains a simple application that:

* Uses a mysql database
* Insert some database records on application startup time
* Expose data via `JSON` routes.
* [Query DSL](http://www.querydsl.com)

[App.java](https://github.com/prasvats/hibernate-starter/blob/master/src/main/java/starter/hbm/App.java):

```
  /**
   * Insert some data on startup:
   */
  onStart(() -> {
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

```

## run

    mvn jooby:run


