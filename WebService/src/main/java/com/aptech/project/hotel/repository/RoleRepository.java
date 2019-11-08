package com.aptech.project.hotel.repository;

import com.aptech.project.hotel.entity.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("FROM Role t1 JOIN FETCH t1.permissions WHERE t1.deleted = 0 AND t1.roleName LIKE %:name%")
    List<Role> listAll(@Param("name") String name, Pageable pageable);
    @Query("SELECT count(t1) FROM Role t1 WHERE t1.deleted = 0 AND t1.roleName LIKE %:name% ")
    int countAll(@Param("name") String name);
    @Query("SELECT (COUNT(t1) > 0) FROM Role t1 WHERE t1.roleName = :name")
    boolean existByName(@Param("name") String name);
    @Modifying
    @Transactional
    @Query("UPDATE Role t1 SET t1.deleted = 1, t1.updatedBy = ?2 WHERE t1.id = ?1")
    void delete(int id, String userUpdate);
    @Query("FROM Role t1 WHERE t1.deleted = 0")
    List<Role> roles();
    @Query("FROM Role t1 JOIN FETCH t1.permissions WHERE t1.deleted = 0 AND t1.id = ?1")
    Role findById(int id);
}
