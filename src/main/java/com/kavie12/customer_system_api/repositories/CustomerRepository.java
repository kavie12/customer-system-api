package com.kavie12.customer_system_api.repositories;

import com.kavie12.customer_system_api.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT c FROM Customer c WHERE CONCAT(c.firstName, ' ', c.lastName) LIKE %?1% OR c.phoneNumber LIKE %?1%")
    Page<Customer> findAllCustomers(String searchKey, Pageable pageable);

    @Query("SELECT COUNT(*) FROM Customer c WHERE CONCAT(c.firstName, ' ', c.lastName) LIKE %?1% OR c.phoneNumber LIKE %?1%")
    int countAllCustomers(String searchText);

}