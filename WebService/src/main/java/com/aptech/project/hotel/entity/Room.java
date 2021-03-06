package com.aptech.project.hotel.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "t_room")
@Getter
@Setter
@Where(clause = "deleted = 0")
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

    @Column(name = "hourly_price")
    private int hourlyPrice;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "t_room_supplies",
            joinColumns = { @JoinColumn(name = "room_id") },
            inverseJoinColumns = { @JoinColumn(name = "supplies_id") })
    private Set<Supplies> supplies;

    @Column(name = "description")
    private String description;
}
