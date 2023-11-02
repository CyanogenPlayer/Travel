package dev.cyan.travel.DTO;

import dev.cyan.travel.entity.Country;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CountryDTO {
    private String id;
    private String name;
}
