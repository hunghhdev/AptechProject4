package com.aptech.project.hotel.converter;

import com.aptech.project.hotel.entity.Customer;
import com.aptech.project.hotel.model.CustomerDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter {

    public Customer toCustomer(CustomerDto customerDto){
        Customer customer = new Customer();
        customer.setId(customerDto.getId());
        customer.setCustomerName(customerDto.getName());
        customer.setPhoneNumber(customerDto.getPhone());
        return customer;
    }

    public CustomerDto toCustomerDto(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getCustomerName());
        customerDto.setPhone(customer.getPhoneNumber());
        return customerDto;
    }
}
