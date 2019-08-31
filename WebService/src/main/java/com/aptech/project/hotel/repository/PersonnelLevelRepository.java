package com.aptech.project.hotel.repository;

import com.aptech.project.hotel.entity.PersonnelLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonnelLevelRepository extends JpaRepository<PersonnelLevel, Integer> {

    @Query("FROM PersonnelLevel t1 where t1.id >= ?1")
    List<PersonnelLevel> findAllWithId(int id);
}
