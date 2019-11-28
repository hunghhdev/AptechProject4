package com.aptech.project.hotel.repository;

import com.aptech.project.hotel.entity.Customer;
import com.aptech.project.hotel.model.CustomerDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT (COUNT(t1) > 0) FROM Customer t1 WHERE t1.deleted = 0 AND t1.identificationNumber = :identification")
    boolean existByIdentification(@Param("identification") String identification);

    @Query("SELECT (COUNT(t1) > 0) FROM Customer t1 WHERE t1.deleted = 0 AND t1.phoneNumber = :phone")
    boolean existByPhone(@Param("phone") String phone);

    @Query("SELECT (COUNT(t1) > 0) FROM Customer t1 WHERE t1.deleted = 0 AND t1.phoneNumber = :phone AND t1.id <> :id")
    boolean existByPhone(@Param("phone") String phone, @Param("id") int id);

    @Query("SELECT NEW com.aptech.project.hotel.model.CustomerDto(t1.id, t1.customerName, t1.phoneNumber, t1.identificationNumber, t1.createdDate, t1.createdBy) FROM Customer t1 " +
            "WHERE t1.deleted = 0 AND t1.createdDate BETWEEN :fromDate AND :toDate")
    List<CustomerDto> findAll(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, Pageable pageable);

    @Query("SELECT count(t1) FROM Customer t1 WHERE t1.deleted = 0 AND t1.createdDate BETWEEN :fromDate AND :toDate")
    int countAll(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}
