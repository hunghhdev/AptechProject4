package com.aptech.project.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.aptech.project.hotel.entity.RoomSupplies;

public interface RoomSuppliesRepository extends JpaRepository<RoomSupplies, Integer>, JpaSpecificationExecutor<RoomSupplies> {

}