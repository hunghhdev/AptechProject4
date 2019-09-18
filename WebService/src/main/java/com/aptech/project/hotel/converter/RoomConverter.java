package com.aptech.project.hotel.converter;

import com.aptech.project.hotel.entity.Room;
import com.aptech.project.hotel.model.RoomDto;
import org.springframework.stereotype.Component;
import java.util.LinkedList;
import java.util.List;

@Component
public class RoomConverter {

    public RoomDto toRoomDto(Room room){
        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setCreatedBy(room.getCreatedBy());
        roomDto.setCreatedDate(room.getCreatedDate());
        roomDto.setBranchId(room.getBranchId());
        roomDto.setName(room.getRoomName());
        roomDto.setCode(room.getRoomCode().split("_")[1]);
        roomDto.setSupplies(room.getSupplies().split(","));
        roomDto.setStatus(room.getStatus());
        roomDto.setDescription(room.getDescription());
        roomDto.setPrice(room.getPrice());
        roomDto.setSize(room.getSize());
        roomDto.setType(room.getRoomType());

        return roomDto;
    }

    public List<RoomDto> toRoomsDto(List<Room> rooms){
        List<RoomDto> roomsDto = new LinkedList<>();
        rooms.forEach(room -> roomsDto.add(toRoomDto(room)));
        return roomsDto;
    }

    public Room toRoom(RoomDto roomDto){
        Room room = new Room();
        room.setId(roomDto.getId());
        room.setRoomName(roomDto.getName());
        room.setRoomCode(roomDto.getCode());
        room.setSupplies(String.join(",", roomDto.getSupplies()));
        room.setStatus(roomDto.getStatus());
        room.setDescription(roomDto.getDescription());
        room.setBranchId(roomDto.getBranchId());
        room.setPrice(roomDto.getPrice());
        room.setSize(roomDto.getSize());
        room.setRoomType(roomDto.getType());

        return room;
    }
}
