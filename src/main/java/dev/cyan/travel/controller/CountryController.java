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
    public ResponseEntity<Optional<Country>> getSingleCountry(@PathVariable ObjectId id) {
        return new ResponseEntity<>(countryService.singleCountry(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Country> createCountry(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<>(countryService.createCountry(payload.get("name")), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Country>> updateCountry(@PathVariable ObjectId id, @RequestBody Country country) {
        return new ResponseEntity<>(countryService.updateCountry(id, country), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Country> deleteCountry(@PathVariable ObjectId id) {
        countryService.deleteCountry(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
