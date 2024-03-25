package com.smartjob.technicaltest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartjob.technicaltest.common.exception.BusinessException;
import com.smartjob.technicaltest.common.exception.ErrorCodesEnum;
import com.smartjob.technicaltest.common.util.Constants;
import com.smartjob.technicaltest.service.JwtTokenUtilService;
import com.smartjob.technicaltest.dto.UserRequestDTO;
import com.smartjob.technicaltest.entity.User;
import com.smartjob.technicaltest.service.CreateUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/technicalTest")
public class Controller {
    private final CreateUserService createUserService;
    private final JwtTokenUtilService jwtTokenUtilService;

    public Controller(CreateUserService createUserService, JwtTokenUtilService jwtTokenUtilService) {
        this.createUserService = createUserService;
        this.jwtTokenUtilService = jwtTokenUtilService;
    }

    private static final Logger logger = Logger.getLogger(Controller.class.getName());

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRequestDTO userRequest) {
        if (Boolean.FALSE.equals(jwtTokenUtilService.getMustGenerateANewKey())) {
            return ResponseEntity.ok(createUserService.createUser(userRequest));
        }else {
            throw new BusinessException(ErrorCodesEnum.MUST_GENERATE_A_NEW_TOKEN);
        }
    }

    @GetMapping("/generateNewSecretKey")
    public ResponseEntity<String> generateNewSecretKey() throws NoSuchAlgorithmException {
        return ResponseEntity.ok(jwtTokenUtilService.getSecretKey());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> error(BusinessException businessException) {
        return new ResponseEntity<>(Map.of(Constants.MESSAGE, businessException.getMessage()), businessException.getCodes().getHttpStatus());
    }

}
