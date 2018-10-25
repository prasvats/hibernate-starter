
# Google Jib Jooby Hibernate Starter


## Quick Preview

This project contains a simple application that:

* Uses a mysql database
* Insert some database records on application startup time
* Expose data via `JSON` routes.
* Sample data on startup [App.java](https://github.com/prasvats/hibernate-starter/blob/master/src/main/java/starter/hbm/App.java):

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
## Settings Application Config

Jooby applications are self-contained and uses default configuration from **conf/application.conf**
  - we can set application configuration as per our environment and then build the artifact
  - we can also pass AWS RDS endpoint here
  - Insert below code snippet, your Database configuration in  **conf/application.conf**
```
#add or overide database config
db.url = "jdbc:mysql://localhost:9015/test"
db.user = "root"
db.password = "root"
```

## Build Docker Image
  - DOCKER_REPOSITORY_USERNAME is the os environment variable for passing username to DOCKER_REPOSITORY_URL(Say **prasvats/jib-hibernate**).
  - You can also change to AWS ECR or Google Container Repositroy.
  - DOCKER_REPOSITORY_PASSWORD is also a os environment variable for passing the password for above username.
  - JIB_VERSION is the os environment variable used for tagging the image.

  ```
export DOCKER_REPOSITORY_USERNAME="" //Pass required value
export DOCKER_REPOSITORY_PASSWORD="" //Pass required value
export JIB_VERSION="" //Pass value
export DOCKER_REPOSITORY_URL="prasvats/jib-hibernate"
mvn compile jib:build   // It will build artifact, docker images as well as push to docker repository.
  ```


## Run you application locally 
1. Non-Docker way 
  - mvn jooby:run 

2. Dokcer way 
  - docker pull $DOCKER_REPOSITORY_URL:$JIB_VERSION
  - docker run -itd -p 8080:8080 $DOCKER_REPOSITORY_URL:$JIB_VERSION
