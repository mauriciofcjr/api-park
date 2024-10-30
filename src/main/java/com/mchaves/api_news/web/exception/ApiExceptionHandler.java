package com.mchaves.api_news.web.exception;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mchaves.api_news.exception.PasswordInvalidException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex,
            HttpServletRequest request, BindingResult result) {

                log.error("Api Error - ", ex);
                return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErroMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Campo(s) invalidos.", result));

    }    

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroMessage> dataIntegrityViolationException(DataIntegrityViolationException ex,
            HttpServletRequest request) {

                log.error("Api Error - ", ex);
                return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErroMessage(request, HttpStatus.CONFLICT, "Username já cadastrado"));

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroMessage> entityNotFoundException(EntityNotFoundException ex,
            HttpServletRequest request) {

                log.error("Api Error - ", ex);
                return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErroMessage(request, HttpStatus.NOT_FOUND, "Usuario não encontrado"));

    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErroMessage> entityNotFoundException(RuntimeException ex,
            HttpServletRequest request) {

                log.error("Api Error - ", ex);
                return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErroMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));

    }

}
