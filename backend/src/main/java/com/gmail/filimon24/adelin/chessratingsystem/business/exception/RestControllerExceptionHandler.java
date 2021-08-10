package com.gmail.filimon24.adelin.chessratingsystem.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class RestControllerExceptionHandler {

    private Map<String, String> createErrorResponse(String message) {
        return Collections.singletonMap("message", message);
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handle(InvalidUserFieldException exception) {
        return createErrorResponse(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handle(UnauthorizedRequestException exception) {
        return createErrorResponse(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handle(UserAlreadyExistsException exception) {
        return createErrorResponse(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handle(UserNotFoundException exception) {
        return createErrorResponse(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handle(GameInvitationNotFoundException exception) {
        return createErrorResponse(exception.getMessage());
    }
}
