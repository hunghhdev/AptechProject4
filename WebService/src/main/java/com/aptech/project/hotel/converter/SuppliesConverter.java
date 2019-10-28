package com.aptech.project.hotel.converter;

import com.aptech.project.hotel.entity.Supplies;
import com.aptech.project.hotel.model.SuppliesDto;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class SuppliesConverter {

    public SuppliesDto toSuppliesDto(Supplies supplies){
        SuppliesDto suppliesDto = new SuppliesDto();
        suppliesDto.setId(supplies.getId());
        suppliesDto.setName(supplies.getName());
        suppliesDto.setNote(supplies.getNote());
        suppliesDto.setQuantity(supplies.getQuantity());
        suppliesDto.setUsed(supplies.getUsed());
        suppliesDto.setCreatedBy(supplies.getCreatedBy());
        suppliesDto.setCreatedDate(supplies.getCreatedDate());
        return suppliesDto;
    }

    public List<SuppliesDto> toSuppliesDtos(List<Supplies> supplies){
        List<SuppliesDto> dtos = new LinkedList<>();
        supplies.forEach(item -> dtos.add(toSuppliesDto(item)));
        return dtos;
    }

    public Supplies toSupplies(SuppliesDto suppliesDto){
        Supplies supplies = new Supplies();
        supplies.setId(suppliesDto.getId());
        supplies.setName(suppliesDto.getName());
        supplies.setQuantity(suppliesDto.getQuantity());
        supplies.setNote(suppliesDto.getNote());
        supplies.setUsed(suppliesDto.getUsed());
        supplies.setCreatedBy(suppliesDto.getCreatedBy());
        supplies.setCreatedDate(suppliesDto.getCreatedDate());
        return supplies;
    }
}
