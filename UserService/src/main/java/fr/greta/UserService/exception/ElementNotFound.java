package fr.greta.UserService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ElementNotFound extends RuntimeException {

    public ElementNotFound(String message){
        super(message);
    }
}
