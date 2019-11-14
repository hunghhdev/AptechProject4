package com.aptech.project.hotel.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.annotation.Id;

@Getter@Setter
@Document(indexName = "customer")
public class CustomerDto {

    @Id
    private int id;
    private String name;
    private String phone;

    public CustomerDto(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public CustomerDto() {
    }
}
