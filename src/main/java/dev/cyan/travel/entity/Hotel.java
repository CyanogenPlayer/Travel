package dev.cyan.travel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document(collection = "hotels")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    @DocumentReference
    private Country country;

    public Hotel(String name, Country country) {
        this.name = name;
        this.country = country;
    }
}
