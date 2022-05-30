package com.dw.ngms.cis.persistence.repository.cart;

import com.dw.ngms.cis.persistence.domains.cart.CartData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 09/02/21, Tue
 **/
@ActiveProfiles("sit")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartDataRepositoryTest {

    @Autowired
    private CartDataRepository cartDataRepository;

    private ObjectMapper mapper;


    @BeforeEach
    public void setup() {
        mapper = new ObjectMapper();

    }

    @Test
    public void givenCartItemJsonData_ConvertToCartItemConfiguration() {

        Collection<CartData> byWorkflowId = cartDataRepository.findByWorkflowId(8070L);

        Assertions.assertThat(byWorkflowId).isNotNull();

    }

}