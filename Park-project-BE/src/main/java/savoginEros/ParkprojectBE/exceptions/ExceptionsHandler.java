package savoginEros.ParkprojectBE.exceptions;

import org.springframework.http.HttpStatus;
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



}
