package com.aptech.project.hotel.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "t_branch_place")
public class BranchPlace extends BaseEntity {
    @Column(name = "branch_name", nullable = false)
    private String branchName;
    @Column(name = "branch_address", nullable = false)
    private String branchAddress;
    @Column(name = "personnel_level")
    private int personnelLevel;
}
