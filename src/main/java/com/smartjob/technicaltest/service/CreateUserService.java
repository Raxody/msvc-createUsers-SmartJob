package com.smartjob.technicaltest.service;

import com.smartjob.technicaltest.dto.PhoneRequestDTO;
import com.smartjob.technicaltest.dto.UserRequestDTO;
import com.smartjob.technicaltest.entity.Phone;
import com.smartjob.technicaltest.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.smartjob.technicaltest.common.util.ArgumentValidator.*;

@Service
public class CreateUserService {
    public static final String NAME = "nombre";
    public static final String EMAIL = "correo electronico";
    public static final String PASSWORD = "contraseña";
    public static final String PHONES = "teléfonos";
    public static final String NUMBER_PHONE_OF_INDEX = "número de télefono de la posición ";
    public static final String CITY_CODE_OF_PHONE = "código de ciudad del télefono ";
    public static final String COUNTRY_CODE_OF_PHONE = "código del país del télefono ";
    public static final int ONE = 1;

    public User createUser(UserRequestDTO userRequest) {

        validateUser(userRequest);

        // Crear la entidad User
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        // ... establecer otros campos si es necesario

        return user;
    }

    private void validateUser(UserRequestDTO userRequest) {
        requireNotNull(userRequest.getName(), NAME);
        onlyAcceptLetters(userRequest.getName(), NAME);

        requireNotNull(userRequest.getEmail(), EMAIL);
        validFormatEmail(userRequest.getEmail(), EMAIL);
        requireNotNull(userRequest.getEmail(), EMAIL);

        requireNotNull(userRequest.getPassword(), PASSWORD);

        listNotEmpty(userRequest.getPhones(), PHONES);

        validatePhones(userRequest.getPhones());
    }

    private void validatePhones(List<PhoneRequestDTO> phones) {
        AtomicInteger index = new AtomicInteger(ONE);
        phones.forEach(phone -> {
            requireNotNull(phone.getNumber(), NUMBER_PHONE_OF_INDEX + index.getAndIncrement());
            requireNumeric(phone.getNumber(), NUMBER_PHONE_OF_INDEX + index.getAndIncrement());
            requirePositiveAndGreaterThanZero(phone.getNumber(), NUMBER_PHONE_OF_INDEX + index.getAndIncrement());

            requireNotNull(phone.getCityCode(), CITY_CODE_OF_PHONE + phone.getNumber());
            requireNumeric(phone.getCityCode(), CITY_CODE_OF_PHONE + phone.getNumber());
            requirePositiveAndGreaterThanZero(phone.getCityCode(), CITY_CODE_OF_PHONE + phone.getNumber());

            requireNotNull(phone.getCountryCode(), COUNTRY_CODE_OF_PHONE + phone.getNumber());
            requireNumeric(phone.getCountryCode(), COUNTRY_CODE_OF_PHONE + phone.getNumber());
            requirePositiveAndGreaterThanZero(phone.getCountryCode(), COUNTRY_CODE_OF_PHONE + phone.getNumber());
        });
    }

}
