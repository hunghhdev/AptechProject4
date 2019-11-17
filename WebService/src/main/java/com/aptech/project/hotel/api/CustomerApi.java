package com.aptech.project.hotel.api;

import com.aptech.project.hotel.converter.CustomerConverter;
import com.aptech.project.hotel.entity.Customer;
import com.aptech.project.hotel.model.CustomerDto;
import com.aptech.project.hotel.model.Data;
import com.aptech.project.hotel.model.ServiceResult;
import com.aptech.project.hotel.model.UserDto;
import com.aptech.project.hotel.service.CustomerService;
import com.aptech.project.hotel.util.Constant;
import com.aptech.project.hotel.util.EnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PreAuthorize("hasAuthority('PERM_CUSTOMER_CREATE')")
    public ResponseEntity<ServiceResult> create(@RequestBody CustomerDto customerDto, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        if (service.existByPhone(customerDto.getPhone())){
            serviceResult.setMessage("Số điện thoại đã tồn tại");
            serviceResult.setStatus(ServiceResult.Status.FAILED);
            return ResponseEntity.ok(serviceResult);
        }
        Customer customer = converter.toCustomer(customerDto);
        customer.setCreatedBy(authentication.getName());
        serviceResult.setMessage("Tạo khách hàng thành công");
        serviceResult.setData(service.saveES(converter.toCustomerDto(service.save(customer))));
        return ResponseEntity.ok(serviceResult);
    }

    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('PERM_CUSTOMER_READ')")
    public ResponseEntity<ServiceResult> list( @RequestParam("page") int page, @RequestParam("size") int size,
            @RequestParam("fromDate") long fromDate, @RequestParam("toDate") long toDate){
        ServiceResult serviceResult = new ServiceResult();
        Pageable pageable = PageRequest.of(--page, size, Sort.by("createdDate").descending());
        int count = service.countAll(Constant.minDate(fromDate), Constant.maxDate(toDate));
        List<CustomerDto> customerDtos = service.findAll(Constant.minDate(fromDate),
                Constant.maxDate(toDate), pageable);
        serviceResult.setData(new Data(count, customerDtos));
        return ResponseEntity.ok(serviceResult);
    }

    @PutMapping(value = "/update")
    @PreAuthorize("hasAuthority('PERM_CUSTOMER_UPDATE')")
    public ResponseEntity<ServiceResult> update(@RequestBody CustomerDto customerDto, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        Customer customer = converter.toCustomer(customerDto);
        customer.setUpdatedBy(authentication.getName());
        serviceResult.setData(service.saveES(converter.toCustomerDto(service.save(customer))));
        serviceResult.setMessage("Cập nhật khách hàng thành công");
        return ResponseEntity.ok(serviceResult);
    }

    @PutMapping(value = "/delete")
    @PreAuthorize("hasAuthority('PERM_CUSTOMER_DELETE')")
    public ResponseEntity<ServiceResult> delete(@RequestParam("id") int id, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        Customer customer = service.findById(id);
        if (customer == null){
            serviceResult.setMessage("Không tìm thấy khách hàng");
            return ResponseEntity.ok(serviceResult);
        }
        customer.setUpdatedBy(authentication.getName());
        customer.setDeleted(true);
        service.save(customer);
        service.deleteES(id);
        serviceResult.setMessage("Xoá khách hàng thành công");
        return ResponseEntity.ok(serviceResult);
    }
}
