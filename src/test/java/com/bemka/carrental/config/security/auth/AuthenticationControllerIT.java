package com.bemka.carrental.config.security.auth;

import com.bemka.carrental.ControllerBaseIT;
import com.bemka.carrental.helper.FileHelper;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "classpath:scripts/insert_user_test_data.sql")
@Sql(scripts = "classpath:scripts/clean_test_data.sql", executionPhase = AFTER_TEST_METHOD)
class AuthenticationControllerIT extends ControllerBaseIT {

    @Test
    public void shouldRegisterNewUser() throws Exception {
        //arrange
        final var requestBody = FileHelper.getFileContentAsString(RESOURCE_PATH + "user/api/api_post_user_register_request201.json");

        //act
        final var result = mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();

        //assert
        final var responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody).isNotNull();
    }

    @Test
    public void shouldThrowException_when_RegisterNewUserNameIsDuplicated() throws Exception {
        //arrange
        final var requestBody = FileHelper.getFileContentAsString(RESOURCE_PATH + "user/api/api_post_user_register_request400.json");
        final var expectedJson = FileHelper.getFileContentAsString(RESOURCE_PATH + "user/api/api_get_user_register_name_not_valid_response400.json");
        //act
        final var result = mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andReturn();

        //assert
        final var responseBody = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedJson, responseBody, false);
    }

    @Test
    public void shouldAuthenticateUser() throws Exception {
        //arrange
        final var requestBody = FileHelper.getFileContentAsString(RESOURCE_PATH + "user/api/api_post_user_authenticate_request200.json");

        //act
        final var result = mockMvc.perform(post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        //assert
        final var responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody).isNotNull();
    }

    @Test
    public void shouldNotAuthenticateUser() throws Exception {
        //arrange
        final var requestBody = FileHelper.getFileContentAsString(RESOURCE_PATH + "user/api/api_post_user_authenticate_request400.json");
        final var expectedJson = FileHelper.getFileContentAsString(RESOURCE_PATH + "user/api/api_get_user_authentication_credentials_not_valid_response403.json");

        //act
        final var result = mockMvc.perform(post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isForbidden())
                .andReturn();

        //assert
        final var responseBody = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedJson, responseBody, false);
    }
}
