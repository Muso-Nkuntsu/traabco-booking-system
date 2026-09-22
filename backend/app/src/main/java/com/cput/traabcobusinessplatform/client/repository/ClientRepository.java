package com.cput.traabcobusinessplatform.client.repository;

import com.cput.traabcobusinessplatform.client.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByFirstNameContainingIgnoreCase(String name);

    List<Client> findByTaxNumberContainingIgnoreCase(String taxNumber);

    Optional<Client> findByEmail(String email);

    boolean existsByEmail(String email);


}
