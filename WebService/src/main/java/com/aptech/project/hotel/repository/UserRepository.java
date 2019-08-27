package com.aptech.project.hotel.repository;

import com.aptech.project.hotel.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("FROM User t1 WHERE t1.deleted = 0 AND t1.username LIKE %:username% AND t1.createdDate BETWEEN :fromDate AND :toDate")
    List<User> findUsers(@Param("username") String username, @Param("fromDate") Date fromDate
            , @Param("toDate") Date toDate, Pageable pageable);
    @Query("SELECT count(*) FROM User t1 WHERE t1.deleted = 0 AND t1.username LIKE %:username% AND t1.createdDate BETWEEN :fromDate AND :toDate")
    int countUsers(@Param("username") String username, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    @Query("FROM User t1 WHERE t1.deleted = 0 AND t1.username = ?1")
    User findByUsername(String username);
    @Query("FROM User t1 WHERE t1.deleted = 0 AND t1.username = ?1 AND t1.password = ?2")
    User findByUsernameAndPassword(String username, String password);
    @Modifying
    @Transactional
    @Query("UPDATE User t1 set t1.jwtKey = ?1 WHERE t1.id = ?2")
    void updateJwtKey(String jwtKey, int id);
    @Modifying
    @Transactional
    @Query("UPDATE User t1 set t1.deleted = 1, t1.updatedBy = ?2 WHERE t1.id = ?1")
    void delete(int id, String userUpdate);
    @Query("SELECT COUNT(*) > 0 FROM User t1 WHERE t1.username = :username")
    boolean existByUsername(@Param("username") String username);
}
