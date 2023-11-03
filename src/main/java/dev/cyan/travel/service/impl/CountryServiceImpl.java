package dev.cyan.travel.service.impl;

import dev.cyan.travel.entity.Country;
import dev.cyan.travel.repository.CountryRepository;
import dev.cyan.travel.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements IService<Country> {
    @Autowired
    private CountryRepository countryRepository;

    @Override
    public List<Country> getAll() {
        return countryRepository.findAll();
    }

    @Override
    public Optional<Country> getById(String id) {
        return countryRepository.findById(id);
    }

    @Override
    public Country createOrUpdate(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public void delete(String id) {
        countryRepository.deleteById(id);
    }
}
