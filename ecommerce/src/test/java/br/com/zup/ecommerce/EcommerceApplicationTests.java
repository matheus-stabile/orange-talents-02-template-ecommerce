package br.com.zup.ecommerce;

import br.com.zup.ecommerce.users.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@SpringBootTest
class EcommerceApplicationTests {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    void contextLoads() {
    }

}
