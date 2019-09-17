package com.aptech.project.hotel.repository;

import com.aptech.project.hotel.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
