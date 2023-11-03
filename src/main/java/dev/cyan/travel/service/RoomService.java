package dev.cyan.travel.service;

import dev.cyan.travel.entity.Hotel;
import dev.cyan.travel.entity.Room;
import dev.cyan.travel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoom(String id) {
        return roomRepository.findById(id);
    }

    public List<Room> getRoomsByHotelId(String id) {
        return roomRepository.findRoomsByHotelId(id);
    }

    public Room createRoom(int roomNumber, int capacity, Hotel hotel) {
        return roomRepository.insert(new Room(roomNumber, capacity, hotel));
    }

    public Room saveRoom(Room updatedRoom) {
        return roomRepository.save(updatedRoom);
    }

    public void deleteRoom(String id) {
        roomRepository.deleteById(id);
    }
}
