package com.aptech.project.hotel.repository;

import com.aptech.project.hotel.entity.Supplies;
import com.aptech.project.hotel.model.SuppliesDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface SuppliesRepository extends JpaRepository<Supplies, Integer> {

    @Query("FROM Supplies t1 WHERE t1.deleted = 0 AND t1.name LIKE %:name% AND t1.createdDate BETWEEN :fromDate AND :toDate")
    List<Supplies> listAll(@Param("name") String name, @Param("fromDate") Date fromDate
            , @Param("toDate") Date toDate, Pageable pageable);

    @Query("FROM Supplies t1 WHERE t1.deleted = 0")
    List<Supplies> listAll();

    @Query("SELECT count(t1) FROM Supplies t1 WHERE t1.deleted = 0 AND t1.name LIKE %:name% AND t1.createdDate BETWEEN :fromDate AND :toDate")
    int countAll(@Param("name") String name, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Modifying
    @Transactional
    @Query("UPDATE Supplies t1 SET t1.deleted = 1, t1.updatedBy = ?2 WHERE t1.id = ?1")
    void delete(int id, String userUpdate);
}
