package com.andersenlab.countriesandcities.controller;

import com.andersenlab.countriesandcities.facade.CityFacade;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ContextConfiguration(classes={TestSecurityConfig.class})
public class CityControllerTest extends AbstractMvcTest {

  @MockBean
  private CityFacade cityFacade;

  @Test
  @SneakyThrows
  void testGetCities() {
    /* Run the test */
    mockMvc.perform(
            get("/api/v1/cities")
        ).
        andExpect(status().isOk());

    /* Verify the results */
    verify(cityFacade, times(1)).getCities(any());
  }

  @Test
  @SneakyThrows
  void testGetUniqueCityNames() {
    /* Run the test */
    mockMvc.perform(
            get("/api/v1/cities/unique")
        ).
        andExpect(status().isOk());

    /* Verify the results */
    verify(cityFacade, times(1)).getUniqueCityNames();
  }

  @Test
  @SneakyThrows
  void testGetCitiesByCountry() {
    /* Run the test */
    mockMvc.perform(
            get("/api/v1/cities/country")
                .param("name", "Ukraine")
        ).
        andExpect(status().isOk());

    /* Verify the results */
    verify(cityFacade, times(1)).getCitiesByCountry(eq("Ukraine"));
  }

  @Test
  @SneakyThrows
  void testGetAllByCityName() {
    /* Run the test */
    mockMvc.perform(
            get("/api/v1/cities/name")
                .param("name", "Kyiv")
        ).
        andExpect(status().isOk());

    /* Verify the results */
    verify(cityFacade, times(1)).getCitiesByName(eq("Kyiv"));
  }

  @Test
  @SneakyThrows
  void testUpdateCity() {
    /* Run the test */
    mockMvc.perform(patch("/api/v1/cities/1")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .param("cityName", "Denver")
            .content("FILE"))
        .andExpect(status().isOk());

    /* Verify the results */
    verify(cityFacade, times(1)).updateCity(eq(1L), eq("Denver"), any());
  }
}
