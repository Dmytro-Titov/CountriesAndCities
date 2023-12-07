package com.andersenlab.countriesandcities.mapper;

import com.andersenlab.countriesandcities.dto.CityDto;
import com.andersenlab.countriesandcities.model.City;
import org.mapstruct.Mapper;

@Mapper
public interface CityMapper {

  CityDto toDto(City city);
}
