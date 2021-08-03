package com.online.cinema.exception_handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * Контроллер для обработки исключений
 */

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Метод, отвечающий за обработку NotFoundException
     * @param e - перехваченое исключений
     * @return ResponseException,с информацией об ошибке и статус
     */
    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> exceptionHandler(NotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity(new ResponseException(HttpStatus.NOT_FOUND.value(), e.getMessage(), new Date()), HttpStatus.NOT_FOUND);
    }
}
