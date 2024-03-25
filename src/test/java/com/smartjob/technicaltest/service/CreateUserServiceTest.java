package com.smartjob.technicaltest.service;

import com.smartjob.technicaltest.common.exception.BusinessException;
import com.smartjob.technicaltest.dto.PhoneRequestDTO;
import com.smartjob.technicaltest.dto.UserRequestDTO;
import com.smartjob.technicaltest.entity.User;
import com.smartjob.technicaltest.repository.UserCrudRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateUserServiceTest {


    @Mock
    private UserCrudRepository userCrudRepository;

    @Mock
    private JwtTokenUtilService jwtTokenUtilService;

    @InjectMocks
    private CreateUserService createUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createUserService.setREGULAR_EXPRESSION_PASSWORD(".+");
    }

    @Test
    void existUserByEmailWhenUserExistsShouldReturnTrue() {
        // Arrange
        String existingEmail = "existing@example.com";
        when(userCrudRepository.existsByEmail(existingEmail)).thenReturn(true);

        // Act
        boolean result = createUserService.existUserByEmail(existingEmail);

        // Assert
        assertTrue(result);
        verify(userCrudRepository, times(1)).existsByEmail(existingEmail);
    }

    @Test
    void existUserByEmailWhenUserDoesNotExistShouldReturnFalse() {
        // Arrange
        String nonExistingEmail = "nonexisting@example.com";
        when(userCrudRepository.existsByEmail(nonExistingEmail)).thenReturn(false);

        // Act
        boolean result = createUserService.existUserByEmail(nonExistingEmail);

        // Assert
        assertFalse(result);
        verify(userCrudRepository, times(1)).existsByEmail(nonExistingEmail);
    }

    @Test
    void createUserWhenUserExistsShouldThrowBusinessException() {
        // Arrange
        String existingEmail = "existing@example.com";
        UserRequestDTO userRequest = createUserRequest(existingEmail);
        when(userCrudRepository.existsByEmail(existingEmail)).thenReturn(true);

        // Act & Assert
        assertThrows(BusinessException.class, () -> createUserService.createUser(userRequest));

        // Assert
        verify(userCrudRepository, times(1)).existsByEmail(existingEmail);
        verifyNoMoreInteractions(userCrudRepository);
    }

    @Test
    void createUserWhenUserDoesNotExistShouldCreateUser() {
        // Arrange
        String newEmail = "new@example.com";
        UserRequestDTO userRequest = createUserRequest(newEmail);
        when(userCrudRepository.existsByEmail(newEmail)).thenReturn(false);
        when(jwtTokenUtilService.generateToken(any())).thenReturn("token");

        // Act
        User createdUser = createUserService.createUser(userRequest);

        // Assert
        assertNotNull(createdUser);
        assertEquals(newEmail, createdUser.getEmail());
        assertNotNull(createdUser.getUuid());
        assertNotNull(createdUser.getCreationDate());
        assertNotNull(createdUser.getLastLogin());
        assertTrue(createdUser.getActive());
        assertEquals("token", createdUser.getToken());
        verify(userCrudRepository, times(1)).existsByEmail(newEmail);
        verify(userCrudRepository, times(1)).save(createdUser);
        verifyNoMoreInteractions(userCrudRepository);
    }


    private UserRequestDTO createUserRequest(String email) {
        UserRequestDTO userRequest = new UserRequestDTO();
        userRequest.setName("John Doe");
        userRequest.setEmail(email);
        userRequest.setPassword("Password123");
        userRequest.setPhones(Collections.singletonList(createPhoneRequest()));
        return userRequest;
    }

    private PhoneRequestDTO createPhoneRequest() {
        PhoneRequestDTO phoneRequest = new PhoneRequestDTO();
        phoneRequest.setNumber("123456789");
        phoneRequest.setCityCode("123");
        phoneRequest.setCountryCode("1");
        return phoneRequest;
    }

}
