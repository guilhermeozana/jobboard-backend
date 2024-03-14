package com.jobboard.reviewms.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReviewNotFoundException extends RuntimeException{

    public ReviewNotFoundException(String mensagem) {
        super(mensagem);
    }
}
