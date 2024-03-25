package com.smartjob.technicaltest.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCodesEnum {

    NOT_NULL("0001", HttpStatus.BAD_REQUEST, "El campo %s no puede estar vacio"),
    NOT_VALUES_DIFFERENT_FROM_LETTERS("0002", HttpStatus.BAD_REQUEST, "El campo %s no puede contener valores distintos a letras"),
    IT_IS_NOT_AN_EMAIL("0003", HttpStatus.BAD_REQUEST, "El campo %s no tiene el formato valido de correo electronico, ejemplo: usuario@dominio.com"),
    THE_LIST_CANNOT_BE_EMPTY("0003", HttpStatus.BAD_REQUEST, "La lista de %s no puede estar vacia"),
    IT_IS_NOT_NUMERIC("0004", HttpStatus.BAD_REQUEST, "El campo %s debe ser numérico"),
    THE_FIELD_CANNOT_BE_LESS_THAN_OR_EQUAL_TO_ZERO("0005", HttpStatus.BAD_REQUEST, "El campo %s no puede ser menor o igual a cero"),
    THE_EMAIL_IS_ALREADY_REGISTER("0006", HttpStatus.BAD_REQUEST, "El correo ya esta registrado"),
    MUST_GENERATE_A_NEW_TOKEN("0007", HttpStatus.BAD_REQUEST, "Hay un problema con el token de seguridad, por favor contactar con soporte"),
    IT_MUST_NOT_MEET_WITH_THE_CONFIGURED_SPECIFICATIONS("0008", HttpStatus.BAD_REQUEST, "El campo %s no cumple con las especificaciones ingresadas"),;
    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    ErrorCodesEnum(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

}
