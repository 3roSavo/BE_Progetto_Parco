package savoginEros.ParkprojectBE.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import savoginEros.ParkprojectBE.payloads.errors.ErrorResponseDTO;

import java.util.Date;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) //401
    public ErrorResponseDTO handleUnauthorized(UnauthorizedException exception) {
        return new ErrorResponseDTO(exception.getMessage(), new Date());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    public ErrorResponseDTO handleBadRequest(BadRequestException exception) {
        return new ErrorResponseDTO(exception.getMessage(), new Date());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponseDTO handleAccessDenied(AccessDeniedException exception) {
        return new ErrorResponseDTO("Il tuo ruolo non permette di accedere a questa funzionalità!", new Date());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) //404
    public ErrorResponseDTO handleNotFound(NotFoundException exception) {
        return new ErrorResponseDTO(exception.getMessage(), new Date());
    }



}
