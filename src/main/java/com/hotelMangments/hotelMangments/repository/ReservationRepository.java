package com.hotelMangments.hotelMangments.repository;

import com.hotelMangments.hotelMangments.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserIdAndRoomIdAndStartDateBetween(Long userId, Long roomId, LocalDateTime startDate, LocalDateTime endDate);

}
