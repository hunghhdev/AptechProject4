package com.aptech.project.hotel.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_room")
@Getter
@Setter
public class Room extends BaseEntity {

    @Column(name = "room_code")
    private String roomCode;

    @Column(name = "branch_id")
    private int branchId;

    @Column(name = "room_type", nullable = false)
    private String roomType = "";

    @Column(name = "size")
    private int size;

    @Column(name = "status")
    private String status;

    @Column(name = "price")
    private int price;

    @Column(name = "supplies")
    private String supplies;

    @Column(name = "description")
    private String description;
}
