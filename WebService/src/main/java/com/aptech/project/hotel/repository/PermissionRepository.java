package com.aptech.project.hotel.repository;

import com.aptech.project.hotel.entity.Permission;
import com.aptech.project.hotel.model.PermissionDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    @Query("FROM Permission t1 JOIN FETCH t1.children ORDER BY t1.id")
    List<Permission> findAll();

    @Query("SELECT new com.aptech.project.hotel.model.PermissionDto(t1.id, t1.permissionName, t1.permissionKey, t1.parentId)" +
            " FROM Permission t1 WHERE t1.permissionName LIKE %:name% AND t1.permissionKey IS NOT NULL")
    List<PermissionDto> list(@Param("name") String name, Pageable pageable);

    @Query("SELECT count(t1) FROM Permission t1 WHERE t1.permissionName LIKE %:name% AND t1.permissionKey IS NOT NULL")
    int count(@Param("name") String name);
}
