package com.aptech.project.hotel.elasticsearch;

import com.aptech.project.hotel.model.CustomerDto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface CustomerElasticSearch extends ElasticsearchRepository<CustomerDto, Integer> {
    List<CustomerDto> findByPhoneContaining(String phone);
}
