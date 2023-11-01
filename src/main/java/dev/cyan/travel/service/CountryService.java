package dev.cyan.travel.service;

import dev.cyan.travel.entity.Country;
import dev.cyan.travel.repository.CountryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

    public List<Country> allCountries() {
        return countryRepository.findAll();
    }

    public Optional<Country> singleCountry(ObjectId id) {
        return countryRepository.findById(id);
    }

    public Country createCountry(String name) {
        return countryRepository.insert(new Country(name));
    }

    public Optional<Country> updateCountry(ObjectId id, Country updatedCountry) {
        return countryRepository.findById(id)
                .map(existingCountry -> {
                    existingCountry.setName(updatedCountry.getName());
                    return countryRepository.save(existingCountry);
                });
    }

    public void deleteCountry(ObjectId id) {
        countryRepository.deleteById(id);
    }
}
