package com.aptech.project.hotel.repository;

import com.aptech.project.hotel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("from User t1 where t1.deleted = 0 and t1.username = ?1")
    User findByUsername(String username);
    @Query("from User t1 where t1.deleted = 0 and t1.username = ?1 and t1.password = ?2")
    User findByUsernameAndPassword(String username, String password);
}
