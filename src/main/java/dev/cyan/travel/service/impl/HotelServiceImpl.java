package dev.cyan.travel.service.impl;

import dev.cyan.travel.entity.Hotel;
import dev.cyan.travel.repository.HotelRepository;
import dev.cyan.travel.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImpl implements IService<Hotel> {
    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Optional<Hotel> getById(String id) {
        return hotelRepository.findById(id);
    }

    @Override
    public Hotel createOrUpdate(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public void delete(String id) {
        hotelRepository.deleteById(id);
    }

    public List<Hotel> getHotelsByCountryId(String id) {
        return hotelRepository.findHotelsByCountryId(id);
    }
}
