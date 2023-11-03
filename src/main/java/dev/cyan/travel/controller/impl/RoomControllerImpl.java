package dev.cyan.travel.controller.impl;

import dev.cyan.travel.DTO.RoomDTO;
import dev.cyan.travel.controller.IController;
import dev.cyan.travel.converter.DTOConverter;
import dev.cyan.travel.entity.Hotel;
import dev.cyan.travel.entity.Room;
import dev.cyan.travel.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rooms")
public class RoomControllerImpl implements IController<RoomDTO> {
    @Autowired
    private IService<Room> roomService;
    @Autowired
    private IService<Hotel> hotelService;

    @Override
    public ResponseEntity<List<RoomDTO>> getAll() {
        List<Room> rooms = roomService.getAll();
        List<RoomDTO> roomDTOs = rooms.stream()
                .map(DTOConverter::convertRoomToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(roomDTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RoomDTO> getById(String id) {
        Optional<Room> room = roomService.getById(id);
        return room.map(value -> new ResponseEntity<>(DTOConverter.convertRoomToDTO(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new RoomDTO(), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<RoomDTO> create(RoomDTO roomDTO) {
        Optional<Hotel> hotel = hotelService.getById(roomDTO.getHotelId());
        if (hotel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Room createdRoom = roomService.createOrUpdate(new Room(roomDTO.getRoomNumber(), roomDTO.getCapacity(),
                hotel.get()));
        return new ResponseEntity<>(DTOConverter.convertRoomToDTO(createdRoom), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<RoomDTO> update(String id, RoomDTO roomDTO) {
        Optional<Room> existingRoom = roomService.getById(id);
        if (existingRoom.isEmpty()) {
            return new ResponseEntity<>(new RoomDTO(), HttpStatus.NOT_FOUND);
        }

        Optional<Hotel> hotel = hotelService.getById(roomDTO.getHotelId());
        if (hotel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        existingRoom.get().setRoomNumber(roomDTO.getRoomNumber());
        existingRoom.get().setCapacity(roomDTO.getCapacity());
        existingRoom.get().setHotel(hotel.get());

        Room upadtedRoom = roomService.createOrUpdate(existingRoom.get());
        return new ResponseEntity<>(DTOConverter.convertRoomToDTO(upadtedRoom), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        Optional<Room> room = roomService.getById(id);
        if (room.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        roomService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO @GetMapping("/{id}/bookings") write method, when bookings will be ready
}
