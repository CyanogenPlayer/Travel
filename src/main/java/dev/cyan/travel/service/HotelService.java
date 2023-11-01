package dev.cyan.travel.service;

import dev.cyan.travel.entity.Hotel;
import dev.cyan.travel.repository.CountryRepository;
import dev.cyan.travel.repository.HotelRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private CountryRepository countryRepository;

    public List<Hotel> allHotels() {
        return hotelRepository.findAll();
    }

    public Optional<Hotel> singleHotel(ObjectId id) {
        return hotelRepository.findById(id);
    }

    public Hotel createHotel(String name, ObjectId countryId) {
        return hotelRepository.insert(new Hotel(name, countryRepository.findById(countryId).get()));
    }

    public Optional<Hotel> updateHotel(ObjectId id, Hotel updatedHotel) {
        return hotelRepository.findById(id)
                .map(existingHotel -> {
                    existingHotel.setName(updatedHotel.getName());
                    existingHotel.setCountry(countryRepository.findByName(updatedHotel.getCountry().getName()).get());
                    return hotelRepository.save(existingHotel);
                });
    }

    public void deleteHotel(ObjectId id) {
        hotelRepository.deleteById(id);
    }
}
