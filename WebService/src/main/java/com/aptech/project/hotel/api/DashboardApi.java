package com.aptech.project.hotel.api;

import com.aptech.project.hotel.service.BookingService;
import com.aptech.project.hotel.service.CustomerService;
import com.aptech.project.hotel.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.project.hotel.model.ServiceResult;

import java.util.Date;

@RestController
@RequestMapping(Constant.API + "/dashboard")
public class DashboardApi {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/using")
    public ResponseEntity<ServiceResult> using() {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setData(bookingService.dashboardUsing());
        return ResponseEntity.ok(serviceResult);
    }

    @GetMapping(value = "/booking")
    public ResponseEntity<ServiceResult> booking() {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setData(bookingService.dashboardBooked());
        return ResponseEntity.ok(serviceResult);
    }

    @GetMapping(value = "/customer")
    public ResponseEntity<ServiceResult> customer() {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setData(customerService.countAll(new Date(Constant.MIN_DATE_MYSQL), new Date(Constant.MAX_DATE_MYSQL)));
        return ResponseEntity.ok(serviceResult);
    }

    @GetMapping(value = "/count")
    public ResponseEntity<ServiceResult> count() {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setData(bookingService.dashboardNumberOfReservations());
        return ResponseEntity.ok(serviceResult);
    }
}
