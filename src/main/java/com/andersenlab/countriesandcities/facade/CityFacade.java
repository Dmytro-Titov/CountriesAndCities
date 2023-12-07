package com.andersenlab.countriesandcities.facade;

import com.andersenlab.countriesandcities.configuration.aws.S3Service;
import com.andersenlab.countriesandcities.dto.CityDto;
import com.andersenlab.countriesandcities.mapper.CityMapper;
import com.andersenlab.countriesandcities.model.City;
import com.andersenlab.countriesandcities.service.CityService;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CityFacade {

  private final CityService cityService;

  private final CityMapper cityMapper;

  private final S3Service s3Service;

  public Page<CityDto> getCities(Pageable pageable) {
    Page<City> citiesEntities = cityService.getCities(pageable);
    citiesEntities.forEach(this::concatCityLogoUrl);
    return citiesEntities.map(cityMapper::toDto);
  }

  public List<String> getUniqueCityNames() {
    return cityService.getUniqueCityNames();
  }

  public List<CityDto> getCitiesByCountry(String name) {
    List<City> citiesEntities = cityService.getCitiesByCountry(name);
    citiesEntities.forEach(this::concatCityLogoUrl);
    return citiesEntities.stream().map(cityMapper::toDto).collect(Collectors.toList());
  }

  public List<CityDto> getCitiesByName(String name) {
    List<City> citiesEntities = cityService.getCitiesByName(name);
    citiesEntities.forEach(this::concatCityLogoUrl);
    return citiesEntities.stream().map(cityMapper::toDto).collect(Collectors.toList());
  }

  public ResponseEntity<CityDto> updateCity(Long id, String name, MultipartFile logoFile) {
    try {
      City existingCity = cityService.getById(id).orElseThrow();
      String logoKey = logoFile.isEmpty() ? null : s3Service.uploadLogo(existingCity, logoFile);
      CityDto cityDto = new CityDto(id, name, logoKey);
      City updatedCity = cityService.updateCity(cityDto, existingCity);
      concatCityLogoUrl(updatedCity);
      return ResponseEntity.ok(cityMapper.toDto(updatedCity));
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    } catch (IOException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  private void concatCityLogoUrl(City city) {
    String url = s3Service.generateS3Url(city.getLogoKey());
    city.setLogoKey(url);
  }
}
