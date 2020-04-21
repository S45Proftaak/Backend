package com.foodplanner.rest_service;

import com.foodplanner.rest_service.repositories.FoodOrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DataJpaTest
class RestServiceApplicationTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    @Test
    void contextLoads() {
        assertTrue(true);
    }

}
