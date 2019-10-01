package com.aptech.project.hotel.repository;

import com.aptech.project.hotel.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT (COUNT(t1) > 0) FROM Customer t1 WHERE t1.phoneNumber = :phone")
    boolean existByPhone(@Param("phone") String phone);

}
