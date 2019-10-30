package com.aptech.project.hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_room")
@Getter
@Setter
public class Room extends BaseEntity {

    @Column(name = "room_code")
    private String roomCode;

    @Column(name = "room_type", nullable = false)
    private String roomType = "";

    @Column(name = "size")
    private int size;

    @Column(name = "status")
    private String status;

    @Column(name = "price")
    private int price;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "t_room_supplies",
            joinColumns = { @JoinColumn(name = "room_id") },
            inverseJoinColumns = { @JoinColumn(name = "supplies_id") })
    private Set<Supplies> supplies;

    @Column(name = "description")
    private String description;
}
