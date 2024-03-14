package com.jobboard.companyms.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CompanyNotFoundException extends RuntimeException{

    public CompanyNotFoundException(String mensagem) {
        super(mensagem);
    }
}
