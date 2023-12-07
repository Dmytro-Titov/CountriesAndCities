package com.andersenlab.countriesandcities.controller;

import com.andersenlab.countriesandcities.dto.CityDto;
import com.andersenlab.countriesandcities.facade.CityFacade;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/cities")
@RequiredArgsConstructor
public class CityController {

  private final CityFacade cityFacade;

  @GetMapping
  public Page<CityDto> getCities(
      @PageableDefault(size = 20, sort = "id", direction = Direction.ASC) Pageable pageable) {
    return cityFacade.getCities(pageable);
  }

  @GetMapping("/unique")
  public List<String> getUniqueCityNames() {
    return cityFacade.getUniqueCityNames();
  }

  @GetMapping("/country")
  public List<CityDto> getCitiesByCountry(@RequestParam String name) {
    return cityFacade.getCitiesByCountry(name);
  }

  @GetMapping("/name")
  public List<CityDto> getAllByCityName(@RequestParam String name) {
    return cityFacade.getCitiesByName(name);
  }

  @PreAuthorize("hasAuthority('ROLE_EDITOR')")
  @PatchMapping("/{id}")
  public ResponseEntity<CityDto> updateCity(@PathVariable Long id,
      @RequestPart("logoFile") MultipartFile logoFile,
      @RequestParam("cityName") String cityName) {
    return cityFacade.updateCity(id, cityName, logoFile);
  }

}
