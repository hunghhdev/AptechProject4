package com.aptech.project.hotel.api;

import com.aptech.project.hotel.converter.SuppliesConverter;
import com.aptech.project.hotel.entity.Supplies;
import com.aptech.project.hotel.model.Data;
import com.aptech.project.hotel.model.ServiceResult;
import com.aptech.project.hotel.model.SuppliesDto;
import com.aptech.project.hotel.service.SuppliesService;
import com.aptech.project.hotel.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.API+"/supplies")
public class SuppliesApi {

    @Autowired
    private SuppliesService service;

    @Autowired
    private SuppliesConverter converter;

    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('PERM_SUPPLIES_READ')")
    public ResponseEntity<ServiceResult> list(
            @RequestParam("page") int page, @RequestParam("size") int size,
            @RequestParam("name") String name, @RequestParam("fromDate") long fromDate,
            @RequestParam("toDate") long toDate){
        Pageable pageable = PageRequest.of(--page, size, Sort.by("createdDate").descending());
        ServiceResult serviceResult = new ServiceResult();

        serviceResult.setData(new Data(service.countAll(name, Constant.minDate(fromDate), Constant.maxDate(toDate)),
                converter.toSuppliesDtos(service.listAll(name, Constant.minDate(fromDate), Constant.maxDate(toDate), pageable))
                ));
        return ResponseEntity.ok(serviceResult);
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('PERM_SUPPLIES_CREATE')")
    public ResponseEntity<ServiceResult> create(@RequestBody SuppliesDto suppliesDto, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        Supplies supplies = converter.toSupplies(suppliesDto);
        supplies.setCreatedBy(authentication.getName());
        serviceResult.setData(service.saveES(converter.toSuppliesDto(service.save(supplies))));
        return ResponseEntity.ok(serviceResult);
    }

    @PutMapping(value = "/update")
    @PreAuthorize("hasAuthority('PERM_SUPPLIES_UPDATE')")
    public ResponseEntity<ServiceResult> update(@RequestBody SuppliesDto suppliesDto, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        Supplies supplies = converter.toSupplies(suppliesDto);
        supplies.setUpdatedBy(authentication.getName());
        serviceResult.setData(converter.toSuppliesDto(service.save(supplies)));
        serviceResult.setMessage("Cập nhật vật tư thành công");
        return ResponseEntity.ok(serviceResult);
    }

    @PutMapping(value = "/delete")
    @PreAuthorize("hasAuthority('PERM_SUPPLIES_DELETE')")
    public ResponseEntity<ServiceResult> delete(@RequestParam("id") int id, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        service.delete(id, authentication.getName());
        serviceResult.setMessage("Xoá vật tư thành công");
        return ResponseEntity.ok(serviceResult);
    }

    @GetMapping(value = "/list-supplies")
    public ResponseEntity<ServiceResult> listSupplies(){
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setData(converter.toSuppliesDtos(service.listAll()));
        return ResponseEntity.ok(serviceResult);
    }

    @GetMapping(value = "/find")
    public ResponseEntity<ServiceResult> findByNumberPhoneES(@RequestParam("name") String name) {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setData(service.findByName(name));
        return ResponseEntity.ok(serviceResult);
    }
}
