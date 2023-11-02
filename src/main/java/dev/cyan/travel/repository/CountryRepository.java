package dev.cyan.travel.repository;

import dev.cyan.travel.entity.Country;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends MongoRepository<Country, ObjectId> {
    Optional<Country> findCountryByName(String name);
}
