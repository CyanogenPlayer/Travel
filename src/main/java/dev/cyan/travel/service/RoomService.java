package dev.cyan.travel.service;

import dev.cyan.travel.entity.Room;
import dev.cyan.travel.repository.HotelRepository;
import dev.cyan.travel.repository.RoomRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelRepository hotelRepository;

    public List<Room> allRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> singleRoom(ObjectId id) {
        return roomRepository.findById(id);
    }

    public Room createRoom(int roomNumber, int capacity, ObjectId hotelId) {
        return roomRepository.insert(new Room(roomNumber, capacity, hotelRepository.findById(hotelId).get()));
    }

    public Optional<Room> updateRoom(ObjectId id, Room updatedRoom) {
        return roomRepository.findById(id)
                .map(existingRoom -> {
                    existingRoom.setRoomNumber(updatedRoom.getRoomNumber());
                    existingRoom.setCapacity(updatedRoom.getCapacity());
                    existingRoom.setHotel(hotelRepository.findByName(updatedRoom.getHotel().getName()).get());
                    return roomRepository.save(existingRoom);
                });
    }

    public void deleteRoom(ObjectId id) {
        roomRepository.deleteById(id);
    }
}
