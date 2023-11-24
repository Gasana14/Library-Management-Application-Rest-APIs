package com.Library.Management.Rest.APIs.controllers;

import com.Library.Management.Rest.APIs.dtos.BookDto;
import com.Library.Management.Rest.APIs.dtos.ResponseDto;
import com.Library.Management.Rest.APIs.models.Book;
import com.Library.Management.Rest.APIs.services.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/authors/{authorId}/books")
@Tag(
        name = "CRUD REST APIs for Book Resource"
)
public class BookController {
    @Autowired
    private BookService bookService;



    @PostMapping
    public ResponseEntity<ResponseDto> createBook(@PathVariable(value = "authorId") String authorId,@Valid  @RequestBody BookDto bookDto){
        return new ResponseEntity<>(bookService.saveBook(bookDto,authorId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getAllBooksByAuthor(@PathVariable(value = "authorId") String authorId){
        return ResponseEntity.ok(bookService.getBooksByAuthorId(authorId));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<ResponseDto> getBookByIdAndByAuthorId(@PathVariable(value = "authorId") String authorId,@PathVariable(value = "bookId") String bookId){
        return ResponseEntity.ok(bookService.getBookByIdAndAuthorId(bookId,authorId));
    }

    @PutMapping("/{bookId}/update")
    public ResponseEntity<ResponseDto> updateBookByIdAndByAuthorId(
            @PathVariable(value = "authorId") String authorId,
            @PathVariable(value = "bookId") String bookId,
            @Valid @RequestBody BookDto bookDto){
        return ResponseEntity.ok(bookService.updateBookByIdAndAuthorId(bookId,authorId,bookDto));
    }

    @DeleteMapping("/{bookId}/delete")
    public ResponseEntity<ResponseDto> deleteBookByIdAndByAuthorId(@PathVariable(value = "authorId") String authorId,@PathVariable(value = "bookId") String bookId){
        return ResponseEntity.ok(bookService.deleteBookByIdAndAuthorId(bookId,authorId));
    }

}
