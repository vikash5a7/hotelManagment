package com.hotelMangments.hotelMangments.repository;

import com.hotelMangments.hotelMangments.entity.Hotel;
import com.hotelMangments.hotelMangments.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Page<Hotel> findByNameContainingOrLocationContaining(String name, String location, Pageable pageable);
    Optional<Room> findRoomById(Long roomId);
}



