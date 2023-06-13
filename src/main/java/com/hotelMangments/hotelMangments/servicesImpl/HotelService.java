package com.hotelMangments.hotelMangments.servicesImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelMangments.hotelMangments.entity.Hotel;
import com.hotelMangments.hotelMangments.entity.Room;
import com.hotelMangments.hotelMangments.exception.NotFoundException;
import com.hotelMangments.hotelMangments.exception.ResourceNotFoundException;
import com.hotelMangments.hotelMangments.repository.HotelRepository;
import com.hotelMangments.hotelMangments.repository.RoomRepository;
import com.hotelMangments.hotelMangments.request.HotelRequestBody;
import com.hotelMangments.hotelMangments.request.RoomDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;
    private final ObjectMapper mapper;
    private final HistoryEntryService historyEntryService;
    private final RoomRepository roomRepository;

    public HotelService(HotelRepository hotelRepository, ObjectMapper mapper, HistoryEntryService historyEntryService, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.mapper = mapper;
        this.historyEntryService = historyEntryService;
        this.roomRepository = roomRepository;
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("sorry !!", "Hotel not found by ", id));
    }

    public Hotel createHotel(HotelRequestBody hotel) {
        Hotel saveHotelInfo = mapper.convertValue(hotel, Hotel.class);
        Hotel saveHotel = hotelRepository.save(saveHotelInfo);
        historyEntryService.saveHistory("New Hotel Added", "A new hotel added name: " + saveHotel.getName() + " ID: " + saveHotel.getId());
        return saveHotel;
    }

    public Page<Hotel> searchHotels(String name, String location, Pageable pageable) {
        return hotelRepository.findByNameContainingOrLocationContaining(name, location, pageable);
    }

    public Room addRoomToHotel(Long hotelId, RoomDetails room) {
        Room saveRoom = mapper.convertValue(room, Room.class);
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new NotFoundException("Hotel not found with id: " + hotelId));
        saveRoom.setHotel(hotel);
        Room saveRoomDetails = roomRepository.save(saveRoom);
        historyEntryService.saveHistory("New Room Added", "A new room added to hotel name: " + hotel.getName() + " Room number: " + room.getRoomNumber());
        return saveRoomDetails;
    }

    public List<Room> getAllRooms(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new NotFoundException("Hotel not found with id: " + hotelId));
        return hotel.getRooms();
    }

    public Room getRoomById(Long roomId) {
        return hotelRepository.findRoomById(roomId).orElseThrow(() -> new NotFoundException("Room not found with id: " + roomId));
    }

    public Room updateRoom(Long roomId, Room room) {
        Room existingRoom = hotelRepository.findRoomById(roomId).orElseThrow(() -> new NotFoundException("Room not found with id: " + roomId));
        existingRoom.setRoomType(room.getRoomType());
        existingRoom.setCapacity(room.getCapacity());
        existingRoom.setPrice(room.getPrice());
        existingRoom.setAmenities(room.getAmenities());
        historyEntryService.saveHistory("Updated room details ", "Room details updated:  room id " + roomId);
        return room;
    }

    public void deleteRoom(Long roomId) {
        historyEntryService.saveHistory("Deleted room ", "Deleted room Id: " + roomId);
        hotelRepository.deleteById(roomId);
    }
}

