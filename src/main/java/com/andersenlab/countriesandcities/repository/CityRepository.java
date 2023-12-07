package com.andersenlab.countriesandcities.repository;

import com.andersenlab.countriesandcities.model.City;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
  Page<City> findAll(Pageable pageable);
  @Query("SELECT DISTINCT c.name FROM City c")
  List<String> findUniqueCityNames();

  @Query("SELECT c FROM City c WHERE LOWER(c.country.name) = LOWER(:name)")
  List<City> findCitiesByCountryName(@Param("name") String name);

  @Query("SELECT c FROM City c WHERE LOWER(c.name) = LOWER(:name)")
  List<City> findAllByName(@Param("name") String name);
}
