package com.smartjob.technicaltest.controller;

import com.smartjob.technicaltest.common.exception.BusinessException;
import com.smartjob.technicaltest.dto.UserRequestDTO;
import com.smartjob.technicaltest.service.CreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/prueba")
public class Controller {

    @Autowired
    private CreateUserService createUserService;

    @PostMapping("/a")
    public void insertProduct(@RequestBody UserRequestDTO userRequest) {
        createUserService.createUser(userRequest);
        System.out.println(userRequest);

    }

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> error(BusinessException businessException) {
        return new ResponseEntity<>(Map.of("mensaje", businessException.getCodes().getMessage()), businessException.getCodes().getHttpStatus());
    }

}
