package com.Library.Management.Rest.APIs.controllers;

import com.Library.Management.Rest.APIs.dtos.AuthorDto;
import com.Library.Management.Rest.APIs.dtos.ResponseDto;
import com.Library.Management.Rest.APIs.models.Author;
import com.Library.Management.Rest.APIs.services.AuthorService;
import com.Library.Management.Rest.APIs.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorControllers {

    @Autowired
    private AuthorService authorService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseDto> createAuthor(@Valid @RequestBody AuthorDto authorDto){
       return new ResponseEntity<>(authorService.saveAuthor(authorDto), HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<ResponseDto> updateAuthor(@Valid @RequestBody AuthorDto authorDto){
        return ResponseEntity.ok( authorService.saveAuthor(authorDto));
    }

    @GetMapping("")
    public ResponseEntity<ResponseDto> findAll(){
        return ResponseEntity.ok(authorService.findAll());
    }



    @GetMapping("/page")
    public ResponseEntity<ResponseDto> authorPage(
            @RequestParam(value = "pageNo",required = false,defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNo,
            @RequestParam(value = "pageSize",required = false,defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy",required = false,defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) String sortBy,
            @RequestParam(value = "sortDir",required = false,defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) String sortDir
    ){
        return ResponseEntity.ok(authorService.getAllAuthors(pageNo,pageSize,sortBy,sortDir));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> findAuthorById(@PathVariable(value = "id") String authorId){
        return ResponseEntity.ok(authorService.getAuthorById(authorId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ResponseDto> deleteAuthorById(@PathVariable(value = "id") String authorId){
        return ResponseEntity.ok(authorService.deleteAuthor(authorId));
    }


}
