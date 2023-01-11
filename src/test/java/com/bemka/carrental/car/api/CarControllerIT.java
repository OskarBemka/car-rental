package com.bemka.carrental.car.api;

import com.bemka.carrental.ControllerBaseIT;
import com.bemka.carrental.car.model.CarBrand;
import com.bemka.carrental.car.model.dao.CarRepository;
import com.bemka.carrental.helper.FileHelper;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "classpath:scripts/insert_car_test_data.sql")
@Sql(scripts = "classpath:scripts/clean_test_data.sql", executionPhase = AFTER_TEST_METHOD)
class CarControllerIT extends ControllerBaseIT {

    @Autowired
    private CarRepository carRepository;

    @Test
    public void shouldReturn201WithExternalId_when_carAdded() throws Exception {
        //arrange
        final var requestBody = FileHelper.getFileContentAsString(RESOURCE_PATH + "car/api/api_post_car_request201.json");

        //act
        final var result = mockMvc.perform(post("/api/car-rental/car")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();

        //assert
        final var responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody).isNotNull();

        final var persistedObject = carRepository.findByExternalId(responseBody);
        assertThat(persistedObject).hasValueSatisfying(car -> {
            assertThat(car.getBrand()).isEqualTo(CarBrand.VW);
            assertThat(car.getModel()).isEqualTo("passat");
            assertThat(car.getExternalId()).isEqualTo(responseBody);
            assertThat(car.getId()).isNotNull();
            assertThat(car.getIsActive()).isTrue();
            assertThat(car.getCreatedAt()).isBeforeOrEqualTo(OffsetDateTime.now());
            assertThat(car.getUpdatedAt()).isBeforeOrEqualTo(OffsetDateTime.now());
        });
    }

    @Test
    public void shouldReturn400_when_carNotValid() throws Exception {
        //arrange
        final var requestBody = FileHelper.getFileContentAsString(RESOURCE_PATH + "car/api/api_post_car_request400.json");
        final var expectedJson = FileHelper.getFileContentAsString(RESOURCE_PATH + "car/api/api_get_car_not_valid_response400.json");

        //act
        final var result = mockMvc.perform(post("/api/car-rental/car")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andReturn();

        //assert
        final var responseBody = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedJson, responseBody, false);
    }

    @Test
    public void shouldReturn200WithCars() throws Exception {
        //arrange
        final var expectedJson = FileHelper.getFileContentAsString(RESOURCE_PATH + "car/api/api_get_cars_response200.json");
        //act
        final var result = mockMvc.perform(get("/api/car-rental/car")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        //assert
        final var responseBody = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedJson, responseBody, false);
    }

    @Test
    public void shouldReturn200WithExternalId_when_carToUpdateIsValid() throws Exception {
        //arrange
        final var externalId = "c50614de-9b12-4414-8267-9515b6cf4111";
        final var requestBody = FileHelper.getFileContentAsString(RESOURCE_PATH + "car/api/api_put_car_request200.json");

        //act
        final var result = mockMvc.perform(put("/api/car-rental/car/{externalId}", externalId)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        //assert
        final var responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody).isNotNull();

        final var updatedObject = carRepository.findByExternalId(responseBody);
        assertThat(updatedObject).hasValueSatisfying(car -> {
            assertThat(car.getBrand()).isEqualTo(CarBrand.TOYOTA);
            assertThat(car.getModel()).isEqualTo("prius");
            assertThat(car.getExternalId()).isEqualTo(responseBody);
            assertThat(car.getId()).isNotNull();
            assertThat(car.getIsActive()).isTrue();
            assertThat(car.getCreatedAt()).isEqualTo(OffsetDateTime.parse("2023-01-01T12:00:00+01:00"));
            assertThat(car.getUpdatedAt()).isBeforeOrEqualTo(OffsetDateTime.now());
        });
    }

    @Test
    public void shouldReturn400_when_carToUpdateIsNotValid() throws Exception {
        //arrange
        final var externalId = "c50614de-9b12-4414-8267-9515b6cf4111";
        final var requestBody = FileHelper.getFileContentAsString(RESOURCE_PATH + "car/api/api_put_car_request400.json");
        final var expectedJson = FileHelper.getFileContentAsString(RESOURCE_PATH + "car/api/api_get_car_not_valid_response400.json");

        //act
        final var result = mockMvc.perform(put("/api/car-rental/car/{externalId}", externalId)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andReturn();

        //assert
        final var responseBody = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedJson, responseBody, false);
    }

    @Test
    public void shouldReturn404_when_carForUpdateNotFound() throws Exception {
        //arrange
        final var externalId = "wrong external id";
        final var requestBody = FileHelper.getFileContentAsString(RESOURCE_PATH + "car/api/api_put_car_request404.json");
        final var expectedJson = FileHelper.getFileContentAsString(RESOURCE_PATH + "car/api/api_get_car_not_found_response404.json");

        //act
        final var result = mockMvc.perform(put("/api/car-rental/car/{externalId}", externalId)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andReturn();

        //assert
        final var responseBody = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedJson, responseBody, false);
    }
}
