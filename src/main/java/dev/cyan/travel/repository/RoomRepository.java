package dev.cyan.travel.repository;

import dev.cyan.travel.entity.Hotel;
import dev.cyan.travel.entity.Room;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends MongoRepository<Room, ObjectId> {
    Optional<Room> findById(String id);
    List<Room> findRoomsByHotelId(String hotelId);
    void deleteById(String id);
}
