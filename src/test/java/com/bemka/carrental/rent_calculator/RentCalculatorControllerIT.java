package com.bemka.carrental.rent_calculator;

import com.bemka.carrental.ControllerBaseIT;
import com.bemka.carrental.helper.FileHelper;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = {"classpath:scripts/insert_car_test_data.sql", "classpath:scripts/insert_rent_price_test_data.sql"})
@Sql(scripts = "classpath:scripts/clean_test_data.sql", executionPhase = AFTER_TEST_METHOD)
class RentCalculatorControllerIT extends ControllerBaseIT {

    @Test
    public void shouldReturn200_when_calculateRentCost() throws Exception {
        //arrange
        final var carExternalId = "c50614de-9b12-4414-8267-9515b6cf4111";
        final var requestBody = FileHelper.getFileContentAsString(RESOURCE_PATH + "car/api/api_post_rent_period_request200.json");

        //act
        final var result = mockMvc.perform(post("/api/car-rental/rent-calculator/{carExternalId}", carExternalId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        //assert
        final var responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody).isEqualTo(BigDecimal.valueOf(170.0).toString());
    }

    @Test
    public void shouldReturn400_when_rentPeriodIsNotValid() throws Exception {
        //arrange
        final var carExternalId = "c50614de-9b12-4414-8267-9515b6cf4111";
        final var requestBody = FileHelper.getFileContentAsString(RESOURCE_PATH + "car/api/api_post_rent_period_not_valid_request400.json");
        final var expectedJson = FileHelper.getFileContentAsString(RESOURCE_PATH + "car/api/api_get_rent_period_not_valid_response400.json");


        //act
        final var result = mockMvc.perform(post("/api/car-rental/rent-calculator/{carExternalId}", carExternalId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andReturn();

        //assert
        final var responseBody = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedJson, responseBody, false);
    }

    @Test
    public void shouldReturn400_when_rentPeriodStartDateIsAfterEndDate() throws Exception {
        //arrange
        final var carExternalId = "c50614de-9b12-4414-8267-9515b6cf4111";
        final var requestBody = FileHelper.getFileContentAsString(RESOURCE_PATH + "car/api/api_post_rent_period_start_date_after_end_date_request400.json");
        final var expectedJson = FileHelper.getFileContentAsString(RESOURCE_PATH + "car/api/api_get_rent_period_start_date_after_end_date_response400.json");


        //act
        final var result = mockMvc.perform(post("/api/car-rental/rent-calculator/{carExternalId}", carExternalId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andReturn();

        //assert
        final var responseBody = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedJson, responseBody, false);
    }
}
