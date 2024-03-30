package com.project.library.service.impl;

import com.project.library.model.City;
import com.project.library.repository.CityRepository;
import com.project.library.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private CityRepository cityRepository;
    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }
}
