package com.aptech.project.hotel.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Table(name = "t_room_supplies")
@Getter
@Setter
@Entity
public class RoomSupplies extends BaseEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  @Column(name = "room_id", nullable = false)
  private Integer roomId;

  @Column(name = "supplies_id", nullable = false)
  private Integer suppliesId;

}