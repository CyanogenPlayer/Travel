package dev.cyan.travel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document(collection = "rooms")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    private String id;
    private int roomNumber;
    private int capacity;
    @DocumentReference
    private Hotel hotel;

    public Room(int roomNumber, int capacity, Hotel hotel) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.hotel = hotel;
    }
}
