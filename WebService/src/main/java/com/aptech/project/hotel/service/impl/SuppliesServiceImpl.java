package com.aptech.project.hotel.service.impl;

import com.aptech.project.hotel.elasticsearch.SuppliesElasticSearch;
import com.aptech.project.hotel.entity.Supplies;
import com.aptech.project.hotel.model.SuppliesDto;
import com.aptech.project.hotel.repository.SuppliesRepository;
import com.aptech.project.hotel.service.SuppliesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SuppliesServiceImpl implements SuppliesService {

    @Autowired
    private SuppliesRepository repository;

    @Autowired
    private SuppliesElasticSearch elasticSearch;

    @Override
    public List<Supplies> listAll(String name, Date fromDate, Date toDate, Pageable pageable) {
        return repository.listAll(name, fromDate, toDate, pageable);
    }

    @Override
    public List<Supplies> listAll() {
        return repository.listAll();
    }

    @Override
    public int countAll(String name, Date fromDate, Date toDate) {
        return repository.countAll(name, fromDate, toDate);
    }

    @Override
    public Supplies save(Supplies supplies) {
        return repository.saveAndFlush(supplies);
    }

    @Override
    public void delete(int id, String usernameUpdate) {
        repository.delete(id, usernameUpdate);
    }

    @Override
    public SuppliesDto saveES(SuppliesDto suppliesDto) {
        return elasticSearch.save(suppliesDto);
    }

    @Override
    public List<SuppliesDto> findByName(String name) {
        return elasticSearch.findByName(name);
    }
}
