package com.andersenlab.countriesandcities.mapper;

import com.andersenlab.countriesandcities.dto.CityDto;
import com.andersenlab.countriesandcities.model.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CityMapper {

  @Mapping(source = "logoKey", target = "logoUrl")
  CityDto toDto(City city);
}
