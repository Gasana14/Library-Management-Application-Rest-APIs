package com.Library.Management.Rest.APIs.exception;

import com.Library.Management.Rest.APIs.dtos.ErrorDetailsDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // handler specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsDto> handleResourceNotFoundException(
            ResourceNotFoundException exception, WebRequest webRequest
    )
    {
        ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto();

        errorDetailsDto.setTimestamp(new Date());
        errorDetailsDto.setMessage(exception.getMessage());
        errorDetailsDto.setDetails(webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetailsDto, HttpStatus.NOT_FOUND);
    }


    // handler specific exception
    @ExceptionHandler(LibraryManagementException.class)
    public ResponseEntity<ErrorDetailsDto> handlerLibraryManagementException(LibraryManagementException exception,WebRequest webRequest){
        ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto();

        errorDetailsDto.setTimestamp(new Date());
        errorDetailsDto.setMessage(exception.getMessage());
        errorDetailsDto.setDetails(webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetailsDto, HttpStatus.BAD_REQUEST);
    }


    // Handle Global exceptions in general (whole application)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailsDto> handleGlobalException(
            Exception exception, WebRequest webRequest
    )
    {
        ErrorDetailsDto errorDetailsDto = new ErrorDetailsDto();

        errorDetailsDto.setTimestamp(new Date());
        errorDetailsDto.setMessage(exception.getMessage());
        errorDetailsDto.setDetails(webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetailsDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError)error).getField();
            String fieldMessage = error.getDefaultMessage();
            errors.put(fieldName,fieldMessage);
        });

        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

}
