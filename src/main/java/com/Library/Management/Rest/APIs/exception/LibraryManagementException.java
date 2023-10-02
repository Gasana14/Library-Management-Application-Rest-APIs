package com.Library.Management.Rest.APIs.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LibraryManagementException extends RuntimeException{
    private HttpStatus status;
    private String message;


}
