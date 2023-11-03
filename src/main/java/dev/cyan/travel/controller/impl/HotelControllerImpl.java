package dev.cyan.travel.controller.impl;

import dev.cyan.travel.DTO.HotelDTO;
import dev.cyan.travel.DTO.RoomDTO;
import dev.cyan.travel.controller.IController;
import dev.cyan.travel.converter.DTOConverter;
import dev.cyan.travel.entity.Country;
import dev.cyan.travel.entity.Hotel;
import dev.cyan.travel.entity.Room;
import dev.cyan.travel.service.IService;
import dev.cyan.travel.service.impl.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hotels")
public class HotelControllerImpl implements IController<HotelDTO> {
    @Autowired
    private IService<Hotel> hotelService;
    @Autowired
    private IService<Country> countryService;
    @Autowired
    private RoomServiceImpl roomService;

    @Override
    public ResponseEntity<List<HotelDTO>> getAll() {
        List<Hotel> hotels = hotelService.getAll();
        List<HotelDTO> hotelDTOs = hotels.stream()
                .map(DTOConverter::convertHotelToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(hotelDTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HotelDTO> getById(String id) {
        Optional<Hotel> hotel = hotelService.getById(id);
        return hotel.map(value -> new ResponseEntity<>(DTOConverter.convertHotelToDTO(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new HotelDTO(), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<HotelDTO> create(HotelDTO hotelDTO) {
        Optional<Country> country = countryService.getById(hotelDTO.getCountryId());
        if (country.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Hotel createdHotel = hotelService.createOrUpdate(new Hotel(hotelDTO.getName(), country.get()));
        return new ResponseEntity<>(DTOConverter.convertHotelToDTO(createdHotel), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HotelDTO> update(String id, HotelDTO hotelDTO) {
        Optional<Hotel> existingHotel = hotelService.getById(id);
        if (existingHotel.isEmpty()) {
            return new ResponseEntity<>(new HotelDTO(), HttpStatus.NOT_FOUND);
        }

        Optional<Country> country = countryService.getById(hotelDTO.getCountryId());
        if (country.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        existingHotel.get().setName(hotelDTO.getName());
        existingHotel.get().setCountry(country.get());

        Hotel updatedHotel = hotelService.createOrUpdate(existingHotel.get());
        return new ResponseEntity<>(DTOConverter.convertHotelToDTO(updatedHotel), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        Optional<Hotel> hotel = hotelService.getById(id);
        if (hotel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        hotelService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/rooms")
    public ResponseEntity<List<RoomDTO>> getRoomsInHotel(@PathVariable String id) {
        Optional<Hotel> hotel = hotelService.getById(id);
        if (hotel.isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
        }

        List<Room> rooms = roomService.getRoomsByHotelId(hotel.get().getId());
        List<RoomDTO> roomDTOs = rooms.stream()
                .map(DTOConverter::convertRoomToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(roomDTOs, HttpStatus.OK);
    }
}
