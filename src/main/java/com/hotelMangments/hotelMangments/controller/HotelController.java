package com.hotelMangments.hotelMangments.controller;

import com.hotelMangments.hotelMangments.entity.Hotel;
import com.hotelMangments.hotelMangments.entity.Room;
import com.hotelMangments.hotelMangments.request.HotelRequestBody;
import com.hotelMangments.hotelMangments.request.RoomDetails;
import com.hotelMangments.hotelMangments.servicesImpl.HotelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long id) {
        Hotel hotel = hotelService.getHotelById(id);
        if (hotel == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hotel);
    }

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@Valid @RequestBody HotelRequestBody hotelRequest) {
        Hotel createdHotel = hotelService.createHotel(hotelRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHotel);
    }

    @PostMapping("/{hotelId}/rooms")
    public ResponseEntity<Hotel> addRoomToHotel(@PathVariable Long hotelId, @RequestBody RoomDetails room) {
        Hotel updatedHotel = hotelService.addRoomToHotel(hotelId, room);
        return ResponseEntity.ok(updatedHotel);
    }

    @GetMapping("/{hotelId}/rooms")
    public ResponseEntity<List<Room>> getAllRooms(@PathVariable Long hotelId) {
        List<Room> rooms = hotelService.getAllRooms(hotelId);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{hotelId}/rooms/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long hotelId, @PathVariable Long roomId) {
        Room room = hotelService.getRoomById(roomId);
        return ResponseEntity.ok(room);
    }

    @PutMapping("/{hotelId}/rooms/{roomId}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long hotelId, @PathVariable Long roomId, @RequestBody Room room) {
        Room updatedRoom = hotelService.updateRoom(roomId, room);
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{hotelId}/rooms/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long hotelId, @PathVariable Long roomId) {
        hotelService.deleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Hotel>> searchHotels(@RequestParam(required = false) String query, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String sortBy, @RequestParam(defaultValue = "asc") String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortOrder), sortBy);
        Page<Hotel> hotels = hotelService.searchHotels(query, query, pageable);
        return ResponseEntity.ok(hotels);
    }

}

