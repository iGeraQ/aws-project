package org.foundations.awsproject.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void createsCustomerThroughRepositoryAbstraction() {
        Customer customer = new Customer("Ada Lovelace", "ada@example.com");
        when(customerRepository.existsByEmail("ada@example.com")).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenReturn(customerWithId(1L, customer));

        CustomerResponse response = customerService.create(new CreateCustomerRequest("Ada Lovelace", "ada@example.com"));

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("Ada Lovelace");
        assertThat(response.email()).isEqualTo("ada@example.com");
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void rejectsDuplicateEmails() {
        when(customerRepository.existsByEmail("ada@example.com")).thenReturn(true);

        assertThatThrownBy(() -> customerService.create(new CreateCustomerRequest("Ada Lovelace", "ada@example.com")))
            .isInstanceOf(ResponseStatusException.class)
            .extracting("statusCode")
            .isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void returnsAllCustomers() {
        when(customerRepository.findAll()).thenReturn(List.of(
            customerWithId(1L, new Customer("Ada Lovelace", "ada@example.com")),
            customerWithId(2L, new Customer("Grace Hopper", "grace@example.com"))
        ));

        List<CustomerResponse> customers = customerService.findAll();

        assertThat(customers).hasSize(2);
        assertThat(customers.getFirst().name()).isEqualTo("Ada Lovelace");
    }

    @Test
    void throwsNotFoundWhenCustomerDoesNotExist() {
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.findById(99L))
            .isInstanceOf(ResponseStatusException.class)
            .extracting("statusCode")
            .isEqualTo(HttpStatus.NOT_FOUND);
    }

    private Customer customerWithId(Long id, Customer customer) {
        try {
            var idField = Customer.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(customer, id);
            return customer;
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException(exception);
        }
    }
}
