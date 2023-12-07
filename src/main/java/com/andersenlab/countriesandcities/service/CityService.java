package com.andersenlab.countriesandcities.service;

import com.andersenlab.countriesandcities.dto.CityDto;
import com.andersenlab.countriesandcities.model.City;
import com.andersenlab.countriesandcities.repository.CityRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityService {

  private final CityRepository cityRepository;

  public Page<City> getCities(Pageable pageable) {
    return cityRepository.findAll(pageable);
  }

  public List<String> getUniqueCityNames() {
    return cityRepository.findUniqueCityNames();
  }

  public List<City> getCitiesByCountry(String name) {
    return cityRepository.findCitiesByCountryName(name);
  }

  public List<City> getCitiesByName(String name) {
    return cityRepository.findAllByName(name);
  }

  public Optional<City> getById(Long id) {
    return cityRepository.findById(id);
  }

  public City updateCity(CityDto newCity, City existingCity) {
    String newCityName = newCity.name();
    String newCityLogoKey = newCity.logoUrl();

    if (StringUtils.isNotEmpty(newCityName)) {
      existingCity.setName(newCityName);
    }
    if (StringUtils.isNotEmpty(newCityLogoKey)) {
      existingCity.setLogoKey(newCityLogoKey);
    }
    return cityRepository.save(existingCity);
  }
}
