package com.aptech.project.hotel.api;

import com.aptech.project.hotel.converter.CustomerConverter;
import com.aptech.project.hotel.entity.Customer;
import com.aptech.project.hotel.model.CustomerDto;
import com.aptech.project.hotel.model.ServiceResult;
import com.aptech.project.hotel.service.CustomerService;
import com.aptech.project.hotel.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.API+"/customer")
public class CustomerApi {

    @Autowired
    private CustomerService service;

    @Autowired
    private CustomerConverter converter;

    @GetMapping(value = "/find")
    public ResponseEntity<ServiceResult> findByNumberPhoneES(@RequestParam("phone") String phoneNumber) {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setData(service.findCustomersDtoES(phoneNumber));
        return ResponseEntity.ok(serviceResult);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<ServiceResult> create(@RequestBody CustomerDto customerDto, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        if (service.existByPhone(customerDto.getPhone())){
            serviceResult.setMessage("Khách hàng đã tồn tại");
            serviceResult.setStatus(ServiceResult.Status.FAILED);
            return ResponseEntity.ok(serviceResult);
        }
        Customer customer = converter.toCustomer(customerDto);
        customer.setCreatedBy(authentication.getName());
        serviceResult.setMessage("Tạo khách hàng thành công");
        serviceResult.setData(service.saveES(converter.toCustomerDto(service.save(customer))));
        return ResponseEntity.ok(serviceResult);
    }
}
