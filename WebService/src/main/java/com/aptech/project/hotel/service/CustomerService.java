package com.aptech.project.hotel.service;

import com.aptech.project.hotel.entity.Customer;
import com.aptech.project.hotel.entity.User;
import com.aptech.project.hotel.model.CustomerDto;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface CustomerService {
    Customer findById(int id);
    Customer save(Customer customer);
    CustomerDto saveES(CustomerDto customerDto);
    void deleteES(int customerId);
    List<CustomerDto> findCustomersDtoES(String phone);
    boolean existByIdentification(String identification);
    boolean existByPhone(String phone);
    boolean existByPhone(String phone, int id);
    List<CustomerDto> findAll(Date fromDate, Date toDate, Pageable pageable);
    int countAll(Date fromDate, Date toDate);
}
