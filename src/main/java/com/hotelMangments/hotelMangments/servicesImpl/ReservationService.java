package com.hotelMangments.hotelMangments.servicesImpl;

import com.hotelMangments.hotelMangments.entity.Reservation;
import com.hotelMangments.hotelMangments.entity.Room;
import com.hotelMangments.hotelMangments.entity.User;
import com.hotelMangments.hotelMangments.exception.NotFoundException;
import com.hotelMangments.hotelMangments.repository.HotelRepository;
import com.hotelMangments.hotelMangments.repository.ReservationRepository;
import com.hotelMangments.hotelMangments.repository.UserRepository;
import com.hotelMangments.hotelMangments.request.ReservationRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository, HotelRepository hotelRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new NotFoundException("Reservation not found with id: " + id));
    }

    public Reservation createReservation(ReservationRequest reservation) {
        Reservation saveReservation = new Reservation();
        User user = userRepository.findById(reservation.getUserId()).orElseThrow(() -> new NotFoundException("Reservation not found with id: " + reservation.getUserId()));
        saveReservation.setStartDate(reservation.getStartDate());
        saveReservation.setGuest(reservation.getGuest());
        saveReservation.setEndDate(reservation.getEndDate());
        Room room = hotelRepository.findRoomById(reservation.getRoomId()).orElseThrow(() -> new NotFoundException("Room not found with id: " + reservation.getRoomId()));
        room.setOccupied(true);
        saveReservation.setUser(user);
        saveReservation.setRoom(room);
        return reservationRepository.save(saveReservation);
    }

    public Reservation updateReservation(Long id, Reservation reservation) {
        Reservation existingReservation = getReservationById(id);
        existingReservation.setStartDate(reservation.getStartDate());
        existingReservation.setEndDate(reservation.getEndDate());
        return reservationRepository.save(existingReservation);
    }

    public void deleteReservation(Long id) {
        Reservation existingReservation = getReservationById(id);
        reservationRepository.delete(existingReservation);
    }

    public List<Reservation> searchReservations(Long userId, Long roomId, LocalDateTime startDate, LocalDateTime endDate) {
        return reservationRepository.findByUserIdAndRoomIdAndStartDateBetween(userId, roomId, startDate, endDate);
    }
}

