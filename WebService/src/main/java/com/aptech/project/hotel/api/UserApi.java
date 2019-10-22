package com.aptech.project.hotel.api;

import com.aptech.project.hotel.converter.UserConverter;
import com.aptech.project.hotel.model.Data;
import com.aptech.project.hotel.model.UserDto;
import com.aptech.project.hotel.entity.User;
import com.aptech.project.hotel.model.ServiceResult;
import com.aptech.project.hotel.model.UserSecurity;
import com.aptech.project.hotel.service.AwsService;
import com.aptech.project.hotel.service.UserService;
import com.aptech.project.hotel.util.ConfigUtility;
import com.aptech.project.hotel.util.Constant;
import com.aptech.project.hotel.util.EnCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(Constant.API+"/user")
public class UserApi {

    private static final Logger logger = LoggerFactory.getLogger(UserApi.class);

    @Autowired
    private UserService service;

    @Autowired
    private AwsService awsService;

    @Autowired
    private UserConverter converter;

    @Autowired
    private ConfigUtility config;


    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('PERM_USER_READ')")
    public ResponseEntity<ServiceResult> list(
            @RequestParam("page") int page, @RequestParam("size") int size,
            @RequestParam("name") String username, @RequestParam("fromDate") long fromDate,
            @RequestParam("toDate") long toDate, Authentication authentication){
        ServiceResult serviceResult = new ServiceResult();
        Pageable pageable = PageRequest.of(--page, size, Sort.by("createdDate").descending());
        int count = service.countUsers(username, Constant.minDate(fromDate), Constant.maxDate(toDate));
        List<UserDto> userDtos = converter.toUsersDto(service.findUsers(username, Constant.minDate(fromDate),
                Constant.maxDate(toDate), pageable));
        serviceResult.setData(new Data(count, userDtos));
        return ResponseEntity.ok(serviceResult);
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('PERM_USER_CREATE')")
    public ResponseEntity<ServiceResult> create(@RequestBody UserDto userDto, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        if (service.existByUsername(userDto.getUsername())){
            serviceResult.setMessage("Tài khoản đã tồn tại");
            serviceResult.setStatus(ServiceResult.Status.FAILED);
            return ResponseEntity.ok(serviceResult);
        }

        User user = converter.toUser(userDto);
        user.setPassword(EnCode.md5(userDto.getPassword()));
        user.setCreatedBy(authentication.getName());
        serviceResult.setData(converter.toUserDto(service.save(user)));
        return ResponseEntity.ok(serviceResult);
    }

    @PutMapping(value = "/update")
    @PreAuthorize("hasAuthority('PERM_USER_UPDATE')")
    public ResponseEntity<ServiceResult> update(@RequestBody UserDto userDto, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        User user = converter.toUser(userDto);
        user.setPassword(EnCode.md5(userDto.getPassword()));
        user.setUpdatedBy(authentication.getName());
        serviceResult.setData(converter.toUserDto(service.save(user)));
        serviceResult.setMessage("Cập nhật tài khoản thành công");
        return ResponseEntity.ok(serviceResult);
    }

    @PutMapping(value = "/delete")
    @PreAuthorize("hasAuthority('PERM_USER_DELETE')")
    public ResponseEntity<ServiceResult> delete(@RequestParam("id") int id, Authentication authentication) {
        ServiceResult serviceResult = new ServiceResult();
        service.delete(id,authentication.getName());
        serviceResult.setMessage("Xoá tài khoản thành công");
        return ResponseEntity.ok(serviceResult);
    }

    @PostMapping("/uploadAvatar")
    public ResponseEntity<?> uploadPhoto(@RequestParam("file") MultipartFile file) {
        ServiceResult serviceResult = new ServiceResult();
        if (file == null) {
            logger.error("file is null");
            return ResponseEntity.badRequest().body("File rỗng");
        }
        File tempAvatar = new File(config.getProperty("temp.avatar"));
        if(!tempAvatar.exists())
        {
            tempAvatar.mkdir();
        }

        File tmpFile = new File(tempAvatar.getAbsolutePath()+"/"+new Date().getTime()+"_"+file.getOriginalFilename());
        try {
            file.transferTo(tmpFile);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        serviceResult.setData(awsService.uploadAvatar(tmpFile, tmpFile.getName()));
        try {
            Files.delete(tmpFile.toPath());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return ResponseEntity.ok(serviceResult);
    }
}
