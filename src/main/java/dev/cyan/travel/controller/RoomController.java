package dev.cyan.travel.controller;

import dev.cyan.travel.entity.Room;
import dev.cyan.travel.service.RoomService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        return new ResponseEntity<>(roomService.allRooms(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Room>> getSingleRoom(@PathVariable ObjectId id) {
        return new ResponseEntity<>(roomService.singleRoom(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<>(roomService.createRoom(
                Integer.parseInt(payload.get("roomNumber")),
                Integer.parseInt(payload.get("capacity")),
                new ObjectId(payload.get("hotelId"))),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Room>> updateRoom(@PathVariable ObjectId id, @RequestBody Room room) {
        return new ResponseEntity<>(roomService.updateRoom(id, room), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Room> deleteRoom(@PathVariable ObjectId id) {
        roomService.deleteRoom(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
