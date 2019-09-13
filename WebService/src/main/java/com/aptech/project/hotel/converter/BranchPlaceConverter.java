package com.aptech.project.hotel.converter;

import com.aptech.project.hotel.entity.BranchPlace;
import com.aptech.project.hotel.model.BranchPlaceDto;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class BranchPlaceConverter {

    public BranchPlaceDto toBranchPlaceDto(BranchPlace branchPlace){
        BranchPlaceDto branchPlaceDto = new BranchPlaceDto();
        branchPlaceDto.setId(branchPlace.getId());
        branchPlaceDto.setBranchName(branchPlace.getBranchName());
        branchPlaceDto.setBranchCode(branchPlace.getBranchCode());
        branchPlaceDto.setBranchAddress(branchPlace.getBranchAddress());
        branchPlaceDto.setCreatedBy(branchPlace.getCreatedBy());
        branchPlaceDto.setCreatedDate(branchPlace.getCreatedDate());
        return branchPlaceDto;
    }

    public List<BranchPlaceDto> toBranchPlacesDto(List<BranchPlace> branchPlaces){
        List<BranchPlaceDto> branchPlaceDto = new LinkedList<>();
        branchPlaces.forEach(branchPlace -> branchPlaceDto.add(toBranchPlaceDto(branchPlace)));
        return branchPlaceDto;
    }

    public BranchPlace toBranchPlace(BranchPlaceDto branchPlaceDto){
        BranchPlace branchPlace = new BranchPlace();
        branchPlace.setId(branchPlaceDto.getId());
        branchPlace.setBranchName(branchPlaceDto.getBranchName());
        branchPlace.setBranchCode(branchPlaceDto.getBranchCode());
        branchPlace.setBranchAddress(branchPlaceDto.getBranchAddress());
        return branchPlace;
    }
}
