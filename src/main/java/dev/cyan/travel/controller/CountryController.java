package dev.cyan.travel.controller;

import dev.cyan.travel.DTO.HotelDTO;
import dev.cyan.travel.converter.DTOConverter;
import dev.cyan.travel.entity.Country;
import dev.cyan.travel.entity.Hotel;
import dev.cyan.travel.service.CountryService;
import dev.cyan.travel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {
    @Autowired
    private CountryService countryService;
    @Autowired
    private HotelService hotelService;

    @GetMapping
    public ResponseEntity<List<Country>> getCountries() {
        return new ResponseEntity<>(countryService.getCountries(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountry(@PathVariable String id) {
        Optional<Country> country = countryService.getCountry(id);
        return country.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/hotels")
    public ResponseEntity<List<HotelDTO>> getHotelsInCountry(@PathVariable String id) {
        Optional<Country> country = countryService.getCountry(id);
        if (country.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Hotel> hotels = hotelService.getHotelsByCountryId(country.get().getId());
        List<HotelDTO> hotelDTOs = hotels.stream()
                .map(DTOConverter::convertHotelToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(hotelDTOs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Country> createCountry(@RequestBody Country country) {
        return new ResponseEntity<>(countryService.createCountry(country.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable String id, @RequestBody Country country) {
        Optional<Country> existingCountry = countryService.getCountry(id);
        if (existingCountry.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        existingCountry.get().setName(country.getName());

        return new ResponseEntity<>(countryService.saveCountry(existingCountry.get()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable String id) {
        Optional<Country> country = countryService.getCountry(id);
        if (country.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        countryService.deleteCountry(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
