package com.Library.Management.Rest.APIs.controllers;

import com.Library.Management.Rest.APIs.dtos.ResponseDto;
import com.Library.Management.Rest.APIs.models.Book;
import com.Library.Management.Rest.APIs.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/authors/{authorId}/books")
public class BookController {
    @Autowired
    private BookService bookService;



    @PostMapping
    public ResponseEntity<ResponseDto> createBook(@PathVariable(value = "authorId") String authorId, @RequestBody Book book){
        return new ResponseEntity<>(bookService.saveBook(book,authorId), HttpStatus.CREATED);
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
            @RequestBody Book book){
        return ResponseEntity.ok(bookService.updateBookByIdAndAuthorId(bookId,authorId,book));
    }

    @DeleteMapping("/{bookId}/delete")
    public ResponseEntity<ResponseDto> deleteBookByIdAndByAuthorId(@PathVariable(value = "authorId") String authorId,@PathVariable(value = "bookId") String bookId){
        return ResponseEntity.ok(bookService.deleteBookByIdAndAuthorId(bookId,authorId));
    }

}
