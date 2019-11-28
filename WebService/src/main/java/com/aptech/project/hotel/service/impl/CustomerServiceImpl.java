package com.aptech.project.hotel.service.impl;

import com.aptech.project.hotel.elasticsearch.CustomerElasticSearch;
import com.aptech.project.hotel.entity.Customer;
import com.aptech.project.hotel.model.CustomerDto;
import com.aptech.project.hotel.repository.CustomerRepository;
import com.aptech.project.hotel.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CustomerElasticSearch customerES;

    @Override
    public Customer findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Customer save(Customer customer) {
        return repository.saveAndFlush(customer);
    }

    @Override
    public CustomerDto saveES(CustomerDto customerDto) {
        return customerES.save(customerDto);
    }

    @Override
    public void deleteES(int customerId) {
        customerES.deleteById(customerId);
    }

    @Override
    public List<CustomerDto> findCustomersDtoES(String phone) {
        return customerES.findByPhoneContaining(phone);
    }

    @Override
    public boolean existByIdentification(String identification) {
        return repository.existByIdentification(identification);
    }

    @Override
    public boolean existByPhone(String phone) {
        return repository.existByPhone(phone);
    }

    @Override
    public boolean existByPhone(String phone, int id) {
        return repository.existByPhone(phone, id);
    }

    @Override
    public List<CustomerDto> findAll(Date fromDate, Date toDate, Pageable pageable) {
        return repository.findAll(fromDate, toDate, pageable);
    }

    @Override
    public int countAll(Date fromDate, Date toDate) {
        return repository.countAll(fromDate, toDate);
    }
}
