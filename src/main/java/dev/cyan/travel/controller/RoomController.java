package dev.cyan.travel.controller;

import dev.cyan.travel.DTO.RoomDTO;
import dev.cyan.travel.converter.DTOConverter;
import dev.cyan.travel.entity.Hotel;
import dev.cyan.travel.entity.Room;
import dev.cyan.travel.service.HotelService;
import dev.cyan.travel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private HotelService hotelService;

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getRooms() {
        List<Room> rooms = roomService.getRooms();
        List<RoomDTO> roomDTOs = rooms.stream()
                .map(DTOConverter::convertRoomToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(roomDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoom(@PathVariable String id) {
        Optional<Room> room = roomService.getRoom(id);
        return room.map(value -> new ResponseEntity<>(DTOConverter.convertRoomToDTO(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // @GetMapping("/{id}/bookings") will be added later

    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomDTO roomDTO) {
        Optional<Hotel> hotel = hotelService.getHotel(roomDTO.getHotelId());
        if (hotel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Room createdRoom = roomService.createRoom(roomDTO.getRoomNumber(), roomDTO.getCapacity(), hotel.get());
        return new ResponseEntity<>(DTOConverter.convertRoomToDTO(createdRoom), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable String id, @RequestBody RoomDTO roomDTO) {
        Optional<Room> existingRoom = roomService.getRoom(id);
        if (existingRoom.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Hotel> hotel = hotelService.getHotel(roomDTO.getHotelId());
        if (hotel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        existingRoom.get().setRoomNumber(roomDTO.getRoomNumber());
        existingRoom.get().setCapacity(roomDTO.getCapacity());
        existingRoom.get().setHotel(hotel.get());

        Room upadtedRoom = roomService.saveRoom(existingRoom.get());
        return new ResponseEntity<>(DTOConverter.convertRoomToDTO(upadtedRoom), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable String id) {
        Optional<Room> room = roomService.getRoom(id);
        if (room.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        roomService.deleteRoom(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
