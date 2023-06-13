package com.hotelMangments.hotelMangments.servicesImpl;

import com.hotelMangments.hotelMangments.entity.*;
import com.hotelMangments.hotelMangments.enums.PaymentStatus;
import com.hotelMangments.hotelMangments.enums.ReservationStatus;
import com.hotelMangments.hotelMangments.exception.NotFoundException;
import com.hotelMangments.hotelMangments.repository.HotelRepository;
import com.hotelMangments.hotelMangments.repository.ReservationRepository;
import com.hotelMangments.hotelMangments.repository.RoomRepository;
import com.hotelMangments.hotelMangments.repository.UserRepository;
import com.hotelMangments.hotelMangments.request.ReservationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final HistoryEntryService historyEntryService;
    private final RoomRepository roomRepository;

    public ReservationService(ReservationRepository reservationRepository, HotelRepository hotelRepository, UserRepository userRepository, HistoryEntryService historyEntryService, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
        this.historyEntryService = historyEntryService;
        this.roomRepository = roomRepository;
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new NotFoundException("Reservation not found with id: " + id));
    }

    public Reservation createReservation(ReservationRequest reservation) {
        log.info("reservation started: {},", reservation);
        Reservation saveReservation = new Reservation();
        User user = userRepository.findById(reservation.getUserId()).orElseThrow(() -> new NotFoundException("Reservation not found with id: " + reservation.getUserId()));
        saveReservation.setStartDate(reservation.getStartDate());
        saveReservation.setEndDate(reservation.getEndDate());
        Room room = roomRepository.findById(reservation.getRoomId()).orElseThrow(() -> new NotFoundException("Room not found with id: " + reservation.getRoomId()));
        if (Objects.nonNull(reservation.getPayment().getPaymentStatus()) && reservation.getPayment().getPaymentStatus().equals(PaymentStatus.SUCCESS)) {
            saveReservation.setStatus(ReservationStatus.CONFIRMED);
            room.setOccupied(true);
        } else if (Objects.nonNull(reservation.getPayment().getPaymentStatus()) && reservation.getPayment().getPaymentStatus().equals(PaymentStatus.PENDING)) {
            saveReservation.setStatus(ReservationStatus.PENDING);
        } else {
            saveReservation.setStatus(ReservationStatus.CANCELED);
        }
        Payment payment = buildPayment(reservation);
        log.info("payment details: {}", payment);
        Guest guest = buildGuest(reservation);
        saveReservation.setGuest(guest);
        saveReservation.setPayment(payment);
        saveReservation.setUser(user);
        saveReservation.setRoom(room);
        Reservation reservation1 = reservationRepository.save(saveReservation);
        historyEntryService.saveHistory("New Reservation", "A room booked id: " + reservation1.getId());
        log.info("successfully reservation done: {}", reservation1.getId());
        return reservation1;
    }

    private Guest buildGuest(ReservationRequest reservation) {
        Guest guest = new Guest();
        if (Objects.nonNull(reservation.getGuest())) {
            guest.setFirstName(reservation.getGuest().getFirstName());
            guest.setLastName(reservation.getGuest().getLastName());
        }
        return guest;
    }

    private Payment buildPayment(ReservationRequest reservation) {
        Payment payment = new Payment();
        if (Objects.nonNull(reservation.getPayment())) {
            payment.setPaymentId(reservation.getPayment().getPaymentId());
            payment.setPaymentStatus(reservation.getPayment().getPaymentStatus());
            payment.setAmount(reservation.getPayment().getAmount());
            payment.setPaymentDate(payment.getPaymentDate());
        }
        return payment;
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

