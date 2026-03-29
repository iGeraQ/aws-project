package org.foundations.awsproject.customer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(JpaCustomerRepository.class)
class JpaCustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void persistsAndQueriesCustomers() {
        Customer savedCustomer = customerRepository.save(new Customer("Ada Lovelace", "ada@example.com"));

        assertThat(savedCustomer.getId()).isNotNull();
        assertThat(customerRepository.existsByEmail("ada@example.com")).isTrue();
        assertThat(customerRepository.findById(savedCustomer.getId())).contains(savedCustomer);
        assertThat(customerRepository.findAll()).hasSize(1);
    }
}
