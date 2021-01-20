package pl.itacademy.tictac.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@ControllerAdvice
//TODO: add handlers for all the exceptions
public class ExceptionResolverHandler extends DefaultHandlerExceptionResolver {

    @ExceptionHandler(PlayerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> playerAlreadyExists(PlayerAlreadyExistsException ex) {
        return ErrorResponse.handle(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<ErrorResponse> playerNotFound(PlayerNotFoundException ex) {
        return ErrorResponse.handle(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ErrorResponse> wrongPassword(WrongPasswordException ex) {
        return ErrorResponse.handle(ex, HttpStatus.UNAUTHORIZED);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ErrorResponse {
        private int status;
        private String error;
        private String message;

        private static ResponseEntity<ErrorResponse> handle(Exception ex, HttpStatus status) {
            return ResponseEntity.status(status).body(new ErrorResponse(status.value(), status.getReasonPhrase(), ex.getMessage()));
        }
    }
}
