package com.aptech.project.hotel.repository;

import com.aptech.project.hotel.entity.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("FROM Role t1 JOIN FETCH t1.permissions WHERE t1.deleted = 0 AND t1.roleName LIKE %:name% AND t1.createdDate BETWEEN :fromDate AND :toDate")
    List<Role> listAll(@Param("name") String name, @Param("fromDate") Date fromDate
            , @Param("toDate") Date toDate, Pageable pageable);
    @Query("SELECT count(t1) FROM Role t1 WHERE t1.deleted = 0 AND t1.roleName LIKE %:name% AND t1.createdDate BETWEEN :fromDate AND :toDate")
    int countAll(@Param("name") String name, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    @Query("SELECT (COUNT(t1) > 0) FROM Role t1 WHERE t1.roleName = :name")
    boolean existByName(@Param("name") String name);
    @Modifying
    @Transactional
    @Query("UPDATE Role t1 SET t1.deleted = 1, t1.updatedBy = ?2 WHERE t1.id = ?1")
    void delete(int id, String userUpdate);
    @Query("FROM Role t1 WHERE t1.deleted = 0")
    List<Role> roles();
    @Query("FROM Role t1 WHERE t1.deleted = 0 and t1.personnelLevel = ?1")
    List<Role> rolesByPersonnelLevel(int personnelLevel);
    @Query("FROM Role t1 JOIN FETCH t1.permissions WHERE t1.deleted = 0 AND t1.id = ?1")
    Role findById(int id);
}
