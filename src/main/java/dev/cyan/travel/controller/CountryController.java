package dev.cyan.travel.controller;

import dev.cyan.travel.entity.Country;
import dev.cyan.travel.service.CountryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping
    public ResponseEntity<List<Country>> getAllCountries() {
        return new ResponseEntity<>(countryService.allCountries(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getSingleCountry(@PathVariable ObjectId id) {
        Optional<Country> country = countryService.singleCountry(id);
        if (country.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(country.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Country> createCountry(@RequestBody Country country) {
        return new ResponseEntity<>(countryService.createCountry(country.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable ObjectId id, @RequestBody Country country) {
        Optional<Country> existingCountry = countryService.singleCountry(id);
        if (existingCountry.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        existingCountry.get().setName(country.getName());

        return new ResponseEntity<>(countryService.saveCountry(existingCountry.get()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Country> deleteCountry(@PathVariable ObjectId id) {
        Optional<Country> country = countryService.singleCountry(id);
        if (country.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        countryService.deleteCountry(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
