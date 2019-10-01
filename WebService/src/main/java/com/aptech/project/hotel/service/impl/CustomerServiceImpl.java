package com.aptech.project.hotel.service.impl;

import com.aptech.project.hotel.elasticsearch.CustomerElasticSearch;
import com.aptech.project.hotel.entity.Customer;
import com.aptech.project.hotel.model.CustomerDto;
import com.aptech.project.hotel.repository.CustomerRepository;
import com.aptech.project.hotel.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CustomerElasticSearch customerES;

    @Override
    public Customer save(Customer customer) {
        return repository.saveAndFlush(customer);
    }

    @Override
    public CustomerDto saveES(CustomerDto customerDto) {
        return customerES.save(customerDto);
    }

    @Override
    public List<CustomerDto> findCustomersDtoES(String phone) {
        return customerES.findByPhoneContaining(phone);
    }

    @Override
    public boolean existByPhone(String phone) {
        return repository.existByPhone(phone);
    }
}
