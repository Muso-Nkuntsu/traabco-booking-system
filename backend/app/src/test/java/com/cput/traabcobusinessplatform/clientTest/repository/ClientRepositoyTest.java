package com.cput.traabcobusinessplatform.clientTest.repository;

import com.cput.traabcobusinessplatform.client.domain.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ClientRepositoyTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        Client jane = Client.builder()
                .email("jane@acme.co.za")
                .taxNumber("TX12345")
                .firstName("Jane")
                .lastName("Doe")
                .companyName("Acme Pty Ltd")
                .address("12 Main Rd, Cape Town")
                .build();

        Client janet = Client.builder()
                .email("janet@beta.co.za")
                .taxNumber("TX99999")
                .firstName("Janet")
                .lastName("Smith")
                .companyName("Beta Ltd")
                .address("5 Long St, Cape Town")
                .build();

        Client bob = Client.builder()
                .email("bob@gamma.co.za")
                .taxNumber("TX55555")
                .firstName("Bob")
                .lastName("Brown")
                .companyName("Gamma Inc")
                .address("9 Short St, Cape Town")
                .build();

        entityManager.persist(jane);
        entityManager.persist(janet);
        entityManager.persist(bob);
        entityManager.flush();
    }

    @Test
    void findByFirstNameContainingIgnoreCase_matchesPartialAndIgnoresCase() {
        List<Client> results = clientRepository.findByFirstNameContainingIgnoreCase("jan");

        assertThat(results)
                .extracting(Client::getFirstName)
                .containsExactlyInAnyOrder("Jane", "Janet");
    }

    @Test
    void findByFirstNameContainingIgnoreCase_returnsEmpty_whenNoMatch() {
        List<Client> results = clientRepository.findByFirstNameContainingIgnoreCase("zzz");

        assertThat(results).isEmpty();
    }

    @Test
    void findByTaxNumberContainingIgnoreCase_matchesPartial() {
        List<Client> results = clientRepository.findByTaxNumberContainingIgnoreCase("999");

        assertThat(results)
                .extracting(Client::getTaxNumber)
                .containsExactly("TX99999");
    }

    @Test
    void existsByEmail_returnsTrue_whenEmailPresent() {
        assertThat(clientRepository.existsByEmail("jane@acme.co.za")).isTrue();
    }

    @Test
    void existsByEmail_returnsFalse_whenEmailAbsent() {
        assertThat(clientRepository.existsByEmail("nobody@nowhere.co.za")).isFalse();
    }

    @Test
    void findByEmail_returnsClient_whenPresent() {
        assertThat(clientRepository.findByEmail("bob@gamma.co.za"))
                .isPresent()
                .get()
                .extracting(Client::getFirstName)
                .isEqualTo("Bob");
    }

    @Test
    void createdAt_isAutoPopulatedOnPersist() {
        Client saved = clientRepository.findByEmail("jane@acme.co.za").orElseThrow();

        assertThat(saved.getCreatedAt()).isNotNull();
    }

}
