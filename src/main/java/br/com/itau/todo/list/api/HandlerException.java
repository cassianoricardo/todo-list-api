package br.com.itau.todo.list.api;

import br.com.itau.todo.list.api.exception.ErrorMessage;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class HandlerException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        var messages = ex.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        var joinMessage = "";

        if(Objects.nonNull(messages)){
            joinMessage = String.join(",", messages);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                             .body(ErrorMessage.builder()
                                               .timeStamp(Instant.now())
                                               .error("Server was not able to process the request, check the params.")
                                               .message(joinMessage)
                                               .build());
    }
}
