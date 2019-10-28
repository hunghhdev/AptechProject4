package com.aptech.project.hotel.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Getter@Setter
@Document(indexName = "supplies")
public class SuppliesDto extends BaseDto {
    private String name;
    private int quantity;
    private String note;
    private int used;
}
