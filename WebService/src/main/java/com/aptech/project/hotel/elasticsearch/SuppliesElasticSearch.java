package com.aptech.project.hotel.elasticsearch;

import com.aptech.project.hotel.model.SuppliesDto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SuppliesElasticSearch extends ElasticsearchRepository<SuppliesDto, Integer> {
    List<SuppliesDto> findByName(String name);
}
