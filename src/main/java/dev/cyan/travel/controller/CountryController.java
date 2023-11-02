package dev.cyan.travel.controller;

import dev.cyan.travel.DTO.CountryDTO;
import dev.cyan.travel.converter.DTOConverter;
import dev.cyan.travel.DTO.HotelDTO;
import dev.cyan.travel.entity.Country;
import dev.cyan.travel.entity.Hotel;
import dev.cyan.travel.service.CountryService;
import dev.cyan.travel.service.HotelService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    public ResponseEntity<List<CountryDTO>> getCountries(
            @RequestParam(name = "id", required = false) ObjectId id,
            @RequestParam(name = "name", required = false) String name) {
        if (id != null) {
            return getCountryResponse(Collections.singletonList(countryService.getCountry(id)
                    .map(DTOConverter::convertCountryToDTO)
                    .orElse(null)));
        }

        if (name != null) {
            return getCountryResponse(Collections.singletonList(countryService.getCountryByName(name)
                    .map(DTOConverter::convertCountryToDTO)
                    .orElse(null)));
        }

        return getAllCountries();
    }

    private ResponseEntity<List<CountryDTO>> getCountryResponse(List<CountryDTO> countryDTOs) {
        return new ResponseEntity<>(countryDTOs, HttpStatus.OK);
    }

    private ResponseEntity<List<CountryDTO>> getAllCountries() {
        List<Country> countries = countryService.getCountries();
        List<CountryDTO> countryDTOs = countries.stream()
                .map(DTOConverter::convertCountryToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(countryDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDTO> getCountry(@PathVariable ObjectId id) {
        Optional<Country> country = countryService.getCountry(id);
        return country.map(value -> new ResponseEntity<>(DTOConverter.convertCountryToDTO(value), HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/hotels")
    public ResponseEntity<List<HotelDTO>> getHotelsInCountry(@PathVariable ObjectId id) {
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
    public ResponseEntity<CountryDTO> createCountry(@RequestBody CountryDTO countryDTO) {
        Country createdCountry = countryService.createCountry(countryDTO.getName());
        return new ResponseEntity<>(DTOConverter.convertCountryToDTO(createdCountry), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryDTO> updateCountry(@PathVariable ObjectId id, @RequestBody CountryDTO countryDTO) {
        Optional<Country> existingCountry = countryService.getCountry(id);
        if (existingCountry.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        existingCountry.get().setName(countryDTO.getName());

        Country updatedCountry = countryService.saveCountry(existingCountry.get());
        return new ResponseEntity<>(DTOConverter.convertCountryToDTO(updatedCountry), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable ObjectId id) {
        Optional<Country> country = countryService.getCountry(id);
        if (country.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        countryService.deleteCountry(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
