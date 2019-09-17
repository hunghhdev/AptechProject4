package com.aptech.project.hotel.service.impl;

import com.aptech.project.hotel.repository.CustomerRepository;
import com.aptech.project.hotel.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository repository;
}
