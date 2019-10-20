package com.aptech.project.hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_permission")
@Getter
@Setter
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false, updatable = false)
    private int id;

    @Column(name = "permission_name", insertable = false, updatable = false)
    private String permissionName = "NULL";

    @Column(name = "permission_key", insertable = false, updatable = false)
    private String permissionKey = "NULL";

    @Column(name = "parent_id", insertable = false, updatable = false)
    private int parentId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnoreProperties("children")
    @org.hibernate.annotations.OrderBy(clause = "id")
    private Set<Permission> children = new HashSet<>();


}
