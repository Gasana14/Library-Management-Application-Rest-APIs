package com.Library.Management.Rest.APIs.controllers;

import com.Library.Management.Rest.APIs.dtos.ResponseDto;
import com.Library.Management.Rest.APIs.dtos.requests.BookDtoRequest;
import com.Library.Management.Rest.APIs.services.BookService;
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
@RequestMapping("/api/authors/{authorId}/books")
@Tag(
        name = "CRUD REST APIs for Book Resource"
)
public class BookController {
    @Autowired
    private BookService bookService;

    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(
            summary = "Create Book REST API",
            description = "Create Book REST API is used to save Book into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 Created"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseDto> createBook(@PathVariable(value = "authorId") String authorId,@Valid  @RequestBody BookDtoRequest bookDtoRequest){
        return new ResponseEntity<>(bookService.saveBook(bookDtoRequest,authorId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getAllBooksByAuthor(@PathVariable(value = "authorId") String authorId){
        return ResponseEntity.ok(bookService.getBooksByAuthorId(authorId));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<ResponseDto> getBookByIdAndByAuthorId(@PathVariable(value = "authorId") String authorId,@PathVariable(value = "bookId") String bookId){
        return ResponseEntity.ok(bookService.getBookByIdAndAuthorId(bookId,authorId));
    }

    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(
            summary = "Update Book REST API",
            description = "Update Book REST API is used to update Book into database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{bookId}/update")
    public ResponseEntity<ResponseDto> updateBookByIdAndByAuthorId(
            @PathVariable(value = "authorId") String authorId,
            @PathVariable(value = "bookId") String bookId,
            @Valid @RequestBody BookDtoRequest bookDtoRequest){
        return ResponseEntity.ok(bookService.updateBookByIdAndAuthorId(bookId,authorId,bookDtoRequest));
    }

    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(
            summary = "Delete Book by Id REST API (PAGE/TABLE)",
            description = "Delete Book by Id  REST API is used to delete Book from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{bookId}/delete")
    public ResponseEntity<ResponseDto> deleteBookByIdAndByAuthorId(@PathVariable(value = "authorId") String authorId,@PathVariable(value = "bookId") String bookId){
        return ResponseEntity.ok(bookService.deleteBookByIdAndAuthorId(bookId,authorId));
    }

}
