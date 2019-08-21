package com.aptech.project.hotel.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "t_user")
public class User extends BaseEntity {
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
//    @Where(clause = "deleted = false")
    private Role role;

    @Column(name = "jwt_key")
    private String jwtKey;
}
