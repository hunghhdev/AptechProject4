package com.aptech.project.hotel.repository;

import com.aptech.project.hotel.entity.BranchPlace;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface BranchPlaceRepository extends JpaRepository<BranchPlace, Integer> {

    @Query("FROM BranchPlace t1 WHERE t1.deleted = 0 AND t1.branchName LIKE %:name% AND t1.createdDate BETWEEN :fromDate AND :toDate")
    List<BranchPlace> listAll(@Param("name") String name, @Param("fromDate") Date fromDate
            , @Param("toDate") Date toDate, Pageable pageable);

    @Query("FROM BranchPlace t1 WHERE t1.deleted = 0 ")
    List<BranchPlace> listAll();

    @Query("FROM BranchPlace t1 WHERE t1.deleted = 0 AND t1.personnelLevel = ?1")
    List<BranchPlace> listAll(int level);

    @Query("SELECT count(t1) FROM BranchPlace t1 WHERE t1.deleted = 0 AND t1.branchName LIKE %:name% AND t1.createdDate BETWEEN :fromDate AND :toDate")
    int countAll(@Param("name") String name, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Modifying
    @Transactional
    @Query("UPDATE BranchPlace t1 SET t1.deleted = 1, t1.updatedBy = ?2 WHERE t1.id = ?1")
    void delete(int id, String userUpdate);

    @Query("FROM BranchPlace t1 WHERE t1.deleted = 0 AND t1.id = ?1")
    List<BranchPlace> findById(int id);

    @Query("SELECT (COUNT(t1) > 0) FROM BranchPlace t1 WHERE t1.branchCode = :code")
    boolean existByBranchCode(@Param("code") String code);
}
