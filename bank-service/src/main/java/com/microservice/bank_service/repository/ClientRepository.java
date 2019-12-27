package com.microservice.bank_service.repository;

import com.microservice.bank_service.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c WHERE c.client_id = ?1")
    Client getClientById(String client_id);

}
