package dev.cyan.travel.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotelDTO {
    private String id;
    private String name;
    private String countryId;
}
