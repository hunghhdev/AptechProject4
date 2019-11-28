package com.aptech.project.hotel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.annotation.Id;

@Getter@Setter
@Document(indexName = "customer")
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    @Id
    private int id;
    private String name;
    private String phone;
    private String identification;

}
