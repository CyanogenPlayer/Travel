package dev.cyan.travel.controller;

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

import java.util.List;
import java.util.Map;
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
    public ResponseEntity<List<HotelDTO>> getAllHotels() {
        List<Hotel> hotels = hotelService.allHotels();
        List<HotelDTO> hotelDTOs = hotels.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(hotelDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDTO> getSingleHotel(@PathVariable ObjectId id) {
        Optional<Hotel> hotel = hotelService.singleHotel(id);
        if (hotel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HotelDTO hotelDTO = convertToDTO(hotel.get());
        return new ResponseEntity<>(hotelDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HotelDTO> createHotel(@RequestBody Map<String, String> payload) {
        Optional<Country> country = countryService.singleCountry(new ObjectId(payload.get("countryId")));
        if (country.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Hotel hotel = hotelService.createHotel(payload.get("name"), country.get());
        HotelDTO hotelDTO = convertToDTO(hotel);
        return new ResponseEntity<>(hotelDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDTO> updateHotel(@PathVariable ObjectId id, @RequestBody HotelDTO hotelDTO) {
        Optional<Hotel> existingHotel = hotelService.singleHotel(id);
        if (existingHotel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Country> country = countryService.singleCountry(new ObjectId(hotelDTO.getCountryId()));
        if (country.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        existingHotel.get().setName(hotelDTO.getName());
        existingHotel.get().setCountry(country.get());

        Hotel updatedHotel = hotelService.saveHotel(existingHotel.get());
        HotelDTO updatedHotelDTO = convertToDTO(updatedHotel);
        return new ResponseEntity<>(updatedHotelDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable ObjectId id) {
        Optional<Hotel> hotel = hotelService.singleHotel(id);
        if (hotel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        hotelService.deleteHotel(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private HotelDTO convertToDTO(Hotel hotel) {
        return new HotelDTO(hotel.getId(), hotel.getName(), hotel.getCountry().getId());
    }
}
