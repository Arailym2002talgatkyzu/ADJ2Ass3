package com.example.demo.repository;

import com.example.demo.repository.dto.Booking;
import com.example.demo.repository.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByUser(User user);
}
