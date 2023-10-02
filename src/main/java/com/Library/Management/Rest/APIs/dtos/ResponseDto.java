package com.Library.Management.Rest.APIs.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDto {
    private Object obj;
    private boolean success;
    private String message;
    private HttpStatus status;


}
