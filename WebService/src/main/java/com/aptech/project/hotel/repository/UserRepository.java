package com.aptech.project.hotel.repository;

import com.aptech.project.hotel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("FROM User t1 JOIN FETCH t1.role WHERE t1.deleted = 0 AND t1.username = ?1")
    User findByUsername(String username);
    @Query("FROM User t1 WHERE t1.deleted = 0 AND t1.username = ?1 AND t1.password = ?2")
    User findByUsernameAndPassword(String username, String password);
    @Modifying
    @Transactional
    @Query("UPDATE User t1 set t1.jwtKey = ?1 WHERE t1.id = ?2")
    void updateJwtKey(String jwtKey, int id);
}
