package com.aptech.project.hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "t_permission")
@Getter
@Setter
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private int id;

    @Column(name = "permission_name")
    private String permissionName = "NULL";

    @Column(name = "permission_key")
    private String permissionKey = "NULL";

    @ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Permission parent;

    @OneToMany(mappedBy="parent")
    @NotNull
    private Set<Permission> children;


}
