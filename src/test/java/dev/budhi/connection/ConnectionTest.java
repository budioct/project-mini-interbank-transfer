package dev.budhi.connection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConnectionTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    void ConnectionTest() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();

        Assertions.assertNotNull(entityManagerFactory);
        Assertions.assertNotNull(entityManager);

    }


}
