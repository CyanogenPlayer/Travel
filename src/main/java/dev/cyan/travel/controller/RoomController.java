package dev.cyan.travel.controller;

import dev.cyan.travel.DTO.RoomDTO;
import dev.cyan.travel.converter.DTOConverter;
import dev.cyan.travel.entity.Hotel;
import dev.cyan.travel.entity.Room;
import dev.cyan.travel.service.HotelService;
import dev.cyan.travel.service.RoomService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<RoomDTO> getRoom(@PathVariable ObjectId id) {
        Optional<Room> room = roomService.getRoom(id);
        if (room.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        RoomDTO roomDTO = DTOConverter.convertRoomToDTO(room.get());
        return new ResponseEntity<>(roomDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomDTO roomDTO) {
        Optional<Hotel> hotel = hotelService.getHotel(new ObjectId(roomDTO.getHotelId()));
        if (hotel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Room createdRoom = roomService.createRoom(roomDTO.getRoomNumber(), roomDTO.getCapacity(), hotel.get());
        RoomDTO createdRoomDTO = DTOConverter.convertRoomToDTO(createdRoom);
        return new ResponseEntity<>(createdRoomDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable ObjectId id, @RequestBody RoomDTO roomDTO) {
        Optional<Room> existingRoom = roomService.getRoom(id);
        if (existingRoom.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Hotel> hotel = hotelService.getHotel(new ObjectId(roomDTO.getHotelId()));
        if (hotel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        existingRoom.get().setRoomNumber(roomDTO.getRoomNumber());
        existingRoom.get().setCapacity(roomDTO.getCapacity());
        existingRoom.get().setHotel(hotel.get());

        Room upadtedRoom = roomService.saveRoom(existingRoom.get());
        RoomDTO upadtedRoomDTO = DTOConverter.convertRoomToDTO(upadtedRoom);
        return new ResponseEntity<>(upadtedRoomDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable ObjectId id) {
        Optional<Room> room = roomService.getRoom(id);
        if (room.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        roomService.deleteRoom(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
