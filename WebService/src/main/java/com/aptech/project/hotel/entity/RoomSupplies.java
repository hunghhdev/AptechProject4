package com.aptech.project.hotel.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Table(name = "t_room_supplies")
@Getter
@Setter
@Entity
public class RoomSupplies implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", insertable = false, nullable = false)
  private int id;

  @Column(name = "room_id", nullable = false)
  private Integer roomId;

  @Column(name = "supplies_id", nullable = false)
  private Integer suppliesId;

}