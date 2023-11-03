package dev.cyan.travel.controller.impl;

import dev.cyan.travel.DTO.CountryDTO;
import dev.cyan.travel.DTO.HotelDTO;
import dev.cyan.travel.controller.IController;
import dev.cyan.travel.converter.DTOConverter;
import dev.cyan.travel.entity.Country;
import dev.cyan.travel.entity.Hotel;
import dev.cyan.travel.service.impl.HotelServiceImpl;
import dev.cyan.travel.service.IService;
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
@RequestMapping("/countries")
public class CountryControllerImpl implements IController<CountryDTO> {
    @Autowired
    private IService<Country> countryService;
    @Autowired
    private HotelServiceImpl hotelService;

    @Override
    public ResponseEntity<List<CountryDTO>> getAll() {
        List<Country> countries = countryService.getAll();
        List<CountryDTO> countryDTOs = countries.stream()
                .map(DTOConverter::convertCountryToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(countryDTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CountryDTO> getById(String id) {
        Optional<Country> country = countryService.getById(id);
        return country.map(value -> new ResponseEntity<>(DTOConverter.convertCountryToDTO(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new CountryDTO(), HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CountryDTO> create(CountryDTO countryDTO) {
        Country createdCountry = countryService.createOrUpdate(new Country(countryDTO.getName()));
        return new ResponseEntity<>(DTOConverter.convertCountryToDTO(createdCountry), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CountryDTO> update(String id, CountryDTO countryDTO) {
        Optional<Country> existingCountry = countryService.getById(id);
        if (existingCountry.isEmpty()) {
            return new ResponseEntity<>(new CountryDTO(), HttpStatus.NOT_FOUND);
        }

        existingCountry.get().setName(countryDTO.getName());

        Country updatedCountry = countryService.createOrUpdate(existingCountry.get());
        return new ResponseEntity<>(DTOConverter.convertCountryToDTO(updatedCountry), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        Optional<Country> country = countryService.getById(id);
        if (country.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        countryService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/hotels")
    public ResponseEntity<List<HotelDTO>> getHotelsInCountry(@PathVariable String id) {
        Optional<Country> country = countryService.getById(id);
        if (country.isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
        }

        List<Hotel> hotels = hotelService.getHotelsByCountryId(country.get().getId());
        List<HotelDTO> hotelDTOs = hotels.stream()
                .map(DTOConverter::convertHotelToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(hotelDTOs, HttpStatus.OK);
    }
}
