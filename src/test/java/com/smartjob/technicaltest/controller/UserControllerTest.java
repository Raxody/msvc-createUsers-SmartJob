package com.smartjob.technicaltest.controller;

import com.smartjob.technicaltest.dto.PhoneRequestDTO;
import com.smartjob.technicaltest.dto.UserRequestDTO;
import com.smartjob.technicaltest.service.CreateUserService;
import com.smartjob.technicaltest.service.JwtTokenUtilService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateUserService createUserService;

    @MockBean
    private JwtTokenUtilService jwtTokenUtilService;

    @Test
    void registerUser_ValidUserRequest_ShouldReturnOk() throws Exception {
        // Arrange
        UserRequestDTO userRequest = new UserRequestDTO("usuario", "correo@example.com",
                "contraseña123", List.of(new PhoneRequestDTO("123456789", "123", "57")));

        // Act - Assert
        mockMvc.perform(post("/technicalTest/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void registerUser_WhenMustGenerateNewToken_ShouldThrowBusinessException() throws Exception {
        // Arrange
        UserRequestDTO userRequest = new UserRequestDTO();
        when(jwtTokenUtilService.getMustGenerateANewKey()).thenReturn(true);
        // Act - Assert
        mockMvc.perform(post("/technicalTest/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void generateNewSecretKey_ShouldReturnOk() throws Exception {
        // Arrange - Act - Assert
        mockMvc.perform(get("/technicalTest/generateNewSecretKey"))
                .andExpect(status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}