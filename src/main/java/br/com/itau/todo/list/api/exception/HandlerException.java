package br.com.itau.todo.list.api.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class HandlerException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        var messages = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                .body(ErrorMessage.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .timeStamp(Instant.now())
                        .error("Not found")
                        .message(ex.getMessage())
                        .path(request.getServletPath())
                        .build());
    }
}
