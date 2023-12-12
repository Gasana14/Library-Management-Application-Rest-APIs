package com.Library.Management.Rest.APIs.controllers;

import com.Library.Management.Rest.APIs.dtos.ResponseDto;
import com.Library.Management.Rest.APIs.dtos.requests.AuthorDtoRequest;
import com.Library.Management.Rest.APIs.services.AuthorService;
import com.Library.Management.Rest.APIs.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
@Tag(
        name = "CRUD REST APIs for Author Resource"
)
public class AuthorControllers {

    @Autowired
    private AuthorService authorService;


    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(
            summary = "Create POST REST API",
            description = "Create Author REST API is used to save Author into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 Created"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseDto> createAuthor(@Valid @RequestBody AuthorDtoRequest authorDtoRequest){
       return new ResponseEntity<>(authorService.saveAuthor(authorDtoRequest), HttpStatus.CREATED);
    }

    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(
            summary = "Update Author REST API",
            description = "Update Author REST API is used to update Author into database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<ResponseDto> updateAuthor(@Valid @RequestBody AuthorDtoRequest authorDtoRequest){
        return ResponseEntity.ok( authorService.saveAuthor(authorDtoRequest));
    }

    @Operation(
            summary = "Find All REST API",
            description = "Find all Authors REST API is used to find all Authors from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    @GetMapping("")
    public ResponseEntity<ResponseDto> findAll(){
        return ResponseEntity.ok(authorService.findAll());
    }



    @Operation(
            summary = "Find All REST API (PAGE/TABLE)",
            description = "Find All Authors REST API is used to find Authors from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    @GetMapping("/page")
    public ResponseEntity<ResponseDto> authorPage(
            @RequestParam(value = "pageNo",required = false,defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNo,
            @RequestParam(value = "pageSize",required = false,defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy",required = false,defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) String sortBy,
            @RequestParam(value = "sortDir",required = false,defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) String sortDir
    ){
        return ResponseEntity.ok(authorService.getAllAuthors(pageNo,pageSize,sortBy,sortDir));
    }


    @Operation(
            summary = "Find Author by Id REST API (PAGE/TABLE)",
            description = "Find Author by Id  REST API is used to find Author from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> findAuthorById(@PathVariable(value = "id") String authorId){
        return ResponseEntity.ok(authorService.getAuthorById(authorId));
    }


    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(
            summary = "Delete Author by Id REST API (PAGE/TABLE)",
            description = "Delete Author by Id  REST API is used to delete Author from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ResponseDto> deleteAuthorById(@PathVariable(value = "id") String authorId){
        return ResponseEntity.ok(authorService.deleteAuthor(authorId));
    }


}
