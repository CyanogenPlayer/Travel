package dev.cyan.travel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "countries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country {
    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    @ReadOnlyProperty
    @DocumentReference(lookup="{'countryId':?#{#self._id} }")
    private List<Hotel> hotels;

    public Country(String name) {
        this.name = name;
    }
}
