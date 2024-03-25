package com.smartjob.technicaltest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartjob.technicaltest.common.exception.BusinessException;
import com.smartjob.technicaltest.common.exception.ErrorCodesEnum;
import com.smartjob.technicaltest.common.util.Constants;
import com.smartjob.technicaltest.dto.PhoneRequestDTO;
import com.smartjob.technicaltest.dto.UserRequestDTO;
import com.smartjob.technicaltest.entity.User;
import com.smartjob.technicaltest.repository.UserCrudRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static com.smartjob.technicaltest.common.util.ArgumentValidator.*;

@Service
public class CreateUserService {

    @Value("${regular.expression.password}")
    private String REGULAR_EXPRESSION_PASSWORD;
    private final UserCrudRepository userCrudRepository;
    private final JwtTokenUtilService jwtTokenUtilService;

    public CreateUserService(UserCrudRepository userCrudRepository, JwtTokenUtilService jwtTokenUtilService) {
        this.userCrudRepository = userCrudRepository;
        this.jwtTokenUtilService = jwtTokenUtilService;
    }

    public boolean existUserByEmail(String email) {
        return userCrudRepository.existsByEmail(email);
    }

    public User createUser(UserRequestDTO userRequest) {

        validateUser(userRequest);

        if (existUserByEmail(userRequest.getEmail())) {
            throw new BusinessException(ErrorCodesEnum.THE_EMAIL_IS_ALREADY_REGISTER);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.convertValue(userRequest, User.class);
        this.generateGenericCreateUser(user);

        userCrudRepository.save(user);
        return user;
    }

    private void validateUser(UserRequestDTO userRequest) {
        requireNotEmpty(userRequest.getName(), Constants.NAME);
        onlyAcceptLetters(userRequest.getName(), Constants.NAME);

        requireNotEmpty(userRequest.getEmail(), Constants.EMAIL);
        validFormatEmail(userRequest.getEmail(), Constants.EMAIL);
        requireNotEmpty(userRequest.getEmail(), Constants.EMAIL);

        requireNotEmpty(userRequest.getPassword(), Constants.PASSWORD);
        mustMeetTheConfiguredSpecifications(REGULAR_EXPRESSION_PASSWORD ,userRequest.getPassword(), Constants.PASSWORD);

        listNotEmpty(userRequest.getPhones(), Constants.PHONES);

        validatePhones(userRequest.getPhones());
    }

    private void validatePhones(List<PhoneRequestDTO> phones) {
        AtomicInteger index = new AtomicInteger(Constants.ONE);
        phones.forEach(phone -> {
            int actualIndex = index.getAndIncrement();
            requireNotEmpty(phone.getNumber(), Constants.NUMBER_PHONE_OF_INDEX + actualIndex);
            requireNumeric(phone.getNumber(), Constants.NUMBER_PHONE_OF_INDEX + actualIndex);
            requirePositiveAndGreaterThanZero(phone.getNumber(), Constants.NUMBER_PHONE_OF_INDEX + actualIndex);

            requireNotEmpty(phone.getCityCode(), Constants.CITY_CODE_OF_PHONE + phone.getNumber());
            requireNumeric(phone.getCityCode(), Constants.CITY_CODE_OF_PHONE + phone.getNumber());
            requirePositiveAndGreaterThanZero(phone.getCityCode(), Constants.CITY_CODE_OF_PHONE + phone.getNumber());

            requireNotEmpty(phone.getCountryCode(), Constants.COUNTRY_CODE_OF_PHONE + phone.getNumber());
            requireNumeric(phone.getCountryCode(), Constants.COUNTRY_CODE_OF_PHONE + phone.getNumber());
            requirePositiveAndGreaterThanZero(phone.getCountryCode(), Constants.COUNTRY_CODE_OF_PHONE + phone.getNumber());
        });
    }

    private void generateGenericCreateUser(User user) {
        user.setUuid(UUID.randomUUID().toString());
        user.setCreationDate(new Date());
        user.setLastLogin(new Date());
        user.setActive(Boolean.TRUE);
        user.setToken(jwtTokenUtilService.generateToken(user));
        user.getPhones().forEach(phone -> phone.setUser(user));
    }

    public void setREGULAR_EXPRESSION_PASSWORD(String REGULAR_EXPRESSION_PASSWORD) {
        this.REGULAR_EXPRESSION_PASSWORD = REGULAR_EXPRESSION_PASSWORD;
    }
}
