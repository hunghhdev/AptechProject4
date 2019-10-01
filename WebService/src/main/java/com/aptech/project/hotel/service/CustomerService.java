package com.aptech.project.hotel.service;

import com.aptech.project.hotel.entity.Customer;
import com.aptech.project.hotel.model.CustomerDto;

import java.util.List;

public interface CustomerService {
    Customer save(Customer customer);
    CustomerDto saveES(CustomerDto customerDto);
    List<CustomerDto> findCustomersDtoES(String phone);
    boolean existByPhone(String phone);
}
