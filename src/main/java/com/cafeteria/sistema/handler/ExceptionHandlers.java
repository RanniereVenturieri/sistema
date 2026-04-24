package com.cafeteria.sistema.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> tratarErro(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ops! O recurso que você procurou não foi encontrado.");
    }
}