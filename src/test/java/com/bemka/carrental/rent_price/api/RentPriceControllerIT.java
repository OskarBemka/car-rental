package com.bemka.carrental.rent_price.api;

import com.bemka.carrental.ControllerBaseIT;
import com.bemka.carrental.helper.FileHelper;
import com.bemka.carrental.rent_price.model.dao.RentPriceRepository;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = {"classpath:scripts/insert_car_test_data.sql", "classpath:scripts/insert_rent_price_test_data.sql"})
@Sql(scripts = "classpath:scripts/clean_test_data.sql", executionPhase = AFTER_TEST_METHOD)
class RentPriceControllerIT extends ControllerBaseIT {

    @Autowired
    private RentPriceRepository rentPriceRepository;

    @Test
    public void shouldReturn201_when_rentPriceAdded() throws Exception {
        //arrange
        final var requestBody = FileHelper.getFileContentAsString(RESOURCE_PATH + "rent_price/api/api_post_rent_price_request201.json");
        final var carExternalId = "c50614de-9b12-4414-8267-9515b6cf4222";

        //act
        final var result = mockMvc.perform(post("/api/car-rental/rent-price/{carExternalId}", carExternalId)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();

        //assert
        final var responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody).isNotNull();

        final var persistedObject = rentPriceRepository.findByExternalId(responseBody);
        assertThat(persistedObject).hasValueSatisfying(rentPrice -> {
            assertThat(rentPrice.getPrice()).isEqualTo(BigDecimal.valueOf(10.0));
            assertThat(rentPrice.getDayOfWeek()).isEqualTo(DayOfWeek.MONDAY);
            assertThat(rentPrice.getExternalId()).isEqualTo(responseBody);
            assertThat(rentPrice.getId()).isNotNull();
            assertThat(rentPrice.getCreatedAt()).isBeforeOrEqualTo(OffsetDateTime.now());
            assertThat(rentPrice.getUpdatedAt()).isBeforeOrEqualTo(OffsetDateTime.now());
        });
    }

    @Test
    public void shouldReturn400_when_rentPriceDtoNotValid() throws Exception {
        //arrange
        final var requestBody = FileHelper.getFileContentAsString(RESOURCE_PATH + "rent_price/api/api_post_rent_price_request400.json");
        final var expectedJson = FileHelper.getFileContentAsString(RESOURCE_PATH + "rent_price/api/api_get_rent_price_not_valid_response400.json");
        final var carExternalId = "c50614de-9b12-4414-8267-9515b6cf4111";

        //act
        final var result = mockMvc.perform(post("/api/car-rental/rent-price/{carExternalId}", carExternalId)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andReturn();

        //assert
        final var responseBody = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedJson, responseBody, false);
    }

    @Test
    public void shouldGetRentPricesForCar_when_carExternalIdProvided() throws Exception {
        //arrange
        final var carExternalId = "c50614de-9b12-4414-8267-9515b6cf4111";
        final var expectedJson = FileHelper.getFileContentAsString(RESOURCE_PATH + "rent_price/api/api_get_rent_prices_for_car_response200.json");

        //act
        final var result = mockMvc.perform(get("/api/car-rental/rent-price/{carExternalId}", carExternalId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        //assert
        final var responseBody = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedJson, responseBody, false);
    }

    @Test
    public void shouldReturn200WithExternalId_when_rentPriceToUpdateIsValid() throws Exception {
        //arrange
        final var externalId = "0c098634-c3da-4f97-9eea-eda6a4monday";
        final var requestBody = FileHelper.getFileContentAsString(RESOURCE_PATH + "rent_price/api/api_put_rent_price_request200.json");

        //act
        final var result = mockMvc.perform(put("/api/car-rental/rent-price/{externalId}", externalId)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        //assert
        final var responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody).isEqualTo(externalId);

        final var persistedObject = rentPriceRepository.findByExternalId(responseBody);
        assertThat(persistedObject).hasValueSatisfying(rentPrice -> {
            assertThat(rentPrice.getPrice()).isEqualTo(BigDecimal.valueOf(99.0));
            assertThat(rentPrice.getDayOfWeek()).isEqualTo(DayOfWeek.MONDAY);
            assertThat(rentPrice.getExternalId()).isEqualTo(responseBody);
            assertThat(rentPrice.getId()).isNotNull();
            assertThat(rentPrice.getCreatedAt()).isEqualTo(OffsetDateTime.parse("2023-01-01T12:00:00+01:00"));
            assertThat(rentPrice.getUpdatedAt()).isBeforeOrEqualTo(OffsetDateTime.now());
        });
    }

    @Test
    public void shouldReturn400_when_rentPriceToUpdateIsNotValid() throws Exception {
        //arrange
        final var externalId = "0c098634-c3da-4f97-9eea-eda6a4monday";
        final var requestBody = FileHelper.getFileContentAsString(RESOURCE_PATH + "rent_price/api/api_put_rent_price_request400.json");
        final var expectedJson = FileHelper.getFileContentAsString(RESOURCE_PATH + "rent_price/api/api_get_rent_price_not_valid_response400.json");

        //act
        final var result = mockMvc.perform(put("/api/car-rental/rent-price/{externalId}", externalId)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andReturn();

        //assert
        final var responseBody = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedJson, responseBody, false);
    }

    @Test
    public void shouldReturn404_when_rentPriceForUpdateNotFound() throws Exception {
        //arrange
        final var externalId = "wrongId";
        final var requestBody = FileHelper.getFileContentAsString(RESOURCE_PATH + "rent_price/api/api_put_rent_price_request404.json");
        final var expectedJson = FileHelper.getFileContentAsString(RESOURCE_PATH + "rent_price/api/api_get_rent_price_not_found_response404.json");

        //act
        final var result = mockMvc.perform(put("/api/car-rental/rent-price/{externalId}", externalId)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andReturn();

        //assert
        final var responseBody = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedJson, responseBody, false);
    }
}
