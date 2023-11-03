package dev.cyan.travel.service.impl;

import dev.cyan.travel.entity.Room;
import dev.cyan.travel.repository.RoomRepository;
import dev.cyan.travel.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements IService<Room> {
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    @Override
    public Optional<Room> getById(String id) {
        return roomRepository.findById(id);
    }

    @Override
    public Room createOrUpdate(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public void delete(String id) {
        roomRepository.deleteById(id);
    }

    public List<Room> getRoomsByHotelId(String id) {
        return roomRepository.findRoomsByHotelId(id);
    }
}
