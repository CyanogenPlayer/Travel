package dev.cyan.travel.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private String id;
    private int roomNumber;
    private int capacity;
    private String hotelId;
}
