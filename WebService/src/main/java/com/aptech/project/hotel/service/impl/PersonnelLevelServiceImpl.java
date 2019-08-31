package com.aptech.project.hotel.service.impl;

import com.aptech.project.hotel.entity.PersonnelLevel;
import com.aptech.project.hotel.repository.PersonnelLevelRepository;
import com.aptech.project.hotel.service.PersonnelLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonnelLevelServiceImpl implements PersonnelLevelService {

    @Autowired
    private PersonnelLevelRepository repository;

    @Override
    public List<PersonnelLevel> list(int id) {
        return repository.findAllWithId(id);
    }
}
