package dev.cyan.travel.service;

import dev.cyan.travel.entity.Country;
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

    public List<Hotel> allHotels() {
        return hotelRepository.findAll();
    }

    public Optional<Hotel> singleHotel(ObjectId id) {
        return hotelRepository.findById(id);
    }

    public Hotel createHotel(String name, Country country) {
        return hotelRepository.insert(new Hotel(name, country));
    }

    public Hotel saveHotel(Hotel updatedHotel) {
        return hotelRepository.save(updatedHotel);
    }

    public void deleteHotel(ObjectId id) {
        hotelRepository.deleteById(id);
    }
}
