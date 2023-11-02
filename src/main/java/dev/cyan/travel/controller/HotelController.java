package dev.cyan.travel.controller;

import dev.cyan.travel.DTO.HotelDTO;
import dev.cyan.travel.converter.DTOConverter;
import dev.cyan.travel.entity.Country;
import dev.cyan.travel.entity.Hotel;
import dev.cyan.travel.service.CountryService;
import dev.cyan.travel.service.HotelService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private CountryService countryService;

    @GetMapping
    public ResponseEntity<List<HotelDTO>> getHotels() {
        List<Hotel> hotels = hotelService.getHotels();
        List<HotelDTO> hotelDTOs = hotels.stream()
                .map(DTOConverter::convertHotelToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(hotelDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDTO> getHotel(@PathVariable ObjectId id) {
        Optional<Hotel> hotel = hotelService.getHotel(id);
        return hotel.map(value -> new ResponseEntity<>(DTOConverter.convertHotelToDTO(value), HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<HotelDTO> createHotel(@RequestBody HotelDTO hotelDTO) {
        Optional<Country> country = countryService.getCountry(hotelDTO.getCountryId());
        if (country.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Hotel createdHotel = hotelService.createHotel(hotelDTO.getName(), country.get());
        return new ResponseEntity<>(DTOConverter.convertHotelToDTO(createdHotel), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDTO> updateHotel(@PathVariable ObjectId id, @RequestBody HotelDTO hotelDTO) {
        Optional<Hotel> existingHotel = hotelService.getHotel(id);
        if (existingHotel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Country> country = countryService.getCountry(hotelDTO.getCountryId());
        if (country.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        existingHotel.get().setName(hotelDTO.getName());
        existingHotel.get().setCountry(country.get());

        Hotel updatedHotel = hotelService.saveHotel(existingHotel.get());
        return new ResponseEntity<>(DTOConverter.convertHotelToDTO(updatedHotel), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable ObjectId id) {
        Optional<Hotel> hotel = hotelService.getHotel(id);
        if (hotel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        hotelService.deleteHotel(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
