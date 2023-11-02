package dev.cyan.travel.service;

import dev.cyan.travel.entity.Country;
import dev.cyan.travel.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

    public List<Country> getCountries() {
        return countryRepository.findAll();
    }

    public Optional<Country> getCountry(String id) {
        return countryRepository.findById(id);
    }

    public Optional<Country> getCountryByName(String name) {
        return countryRepository.findCountryByName(name);
    }

    public Country createCountry(String name) {
        return countryRepository.insert(new Country(name));
    }

    public Country saveCountry(Country updatedCountry) {
        return countryRepository.save(updatedCountry);
    }

    public void deleteCountry(String id) {
        countryRepository.deleteById(id);
    }
}
