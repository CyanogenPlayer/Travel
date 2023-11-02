package dev.cyan.travel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "countries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country {
    @Id
    private String id;
    private String name;

    public Country(String name) {
        this.name = name;
    }
}
