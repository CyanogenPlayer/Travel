package dev.cyan.travel.controller;

import dev.cyan.travel.entity.Hotel;
import dev.cyan.travel.service.HotelService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        return new ResponseEntity<>(hotelService.allHotels(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Hotel>> getSingleHotel(@PathVariable ObjectId id) {
        return new ResponseEntity<>(hotelService.singleHotel(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<>(hotelService.createHotel(
                payload.get("name"),
                new ObjectId(payload.get("countryId"))),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Hotel>> updateHotel(@PathVariable ObjectId id, @RequestBody Hotel hotel) {
        return new ResponseEntity<>(hotelService.updateHotel(id, hotel), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Hotel> deleteHotel(@PathVariable ObjectId id) {
        hotelService.deleteHotel(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
