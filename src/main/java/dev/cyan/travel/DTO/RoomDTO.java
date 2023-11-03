package dev.cyan.travel.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomDTO {
    private String id;
    private int roomNumber;
    private int capacity;
    private String hotelId;
}
