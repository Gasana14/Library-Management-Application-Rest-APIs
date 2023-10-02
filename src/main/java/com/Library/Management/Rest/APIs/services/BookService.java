package com.Library.Management.Rest.APIs.services;

import com.Library.Management.Rest.APIs.dtos.ResponseDto;
import com.Library.Management.Rest.APIs.models.Author;
import com.Library.Management.Rest.APIs.models.Book;
import com.Library.Management.Rest.APIs.repositories.AuthorRepository;
import com.Library.Management.Rest.APIs.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;


    public ResponseDto saveBook(Book book,String authorId){
        ResponseDto responseDto = new ResponseDto();

        // find author to assign to a specific book
        Author author = authorRepository.findById(authorId).orElse(null);

        // check if the book id is null or not
        if(book.getId()!=null){
            // check if book entity exist in our database
            Book existingBook = bookRepository.findById(book.getId()).orElse(null);

            // check if book entity exist in our database
            if(existingBook!=null){
               existingBook = updateExistingBook(existingBook,book,author);
               responseDto.setObj(existingBook);
               responseDto.setStatus(HttpStatus.OK);
               responseDto.setSuccess(true);
               responseDto.setMessage("Book successfully Updated");
            }else{
                responseDto.setObj(null);
                responseDto.setStatus(HttpStatus.BAD_REQUEST);
                responseDto.setSuccess(false);
                responseDto.setMessage("Bad Request, try again");
            }


        }else{
            // Book with no ID provided, create a new book
            book.setAuthor(author);
            Book newBook = bookRepository.save(book);
            responseDto.setMessage("Book is successfully created");
            responseDto.setSuccess(true);
            responseDto.setStatus(HttpStatus.CREATED);
            responseDto.setObj(newBook);
        }



        return responseDto;
    }


    private Book updateExistingBook(Book existingBook,Book updatedBook,Author author){
        existingBook.setCategory(updatedBook.getCategory());
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setPrice(updatedBook.getPrice());
        existingBook.setAuthor(author);
        bookRepository.save(existingBook);
        return existingBook;
    }

    public Set<Book> getBooksByAuthorId(String authorId){
        // get author by id
        Author author = authorRepository.findById(authorId).orElse(null);
        // set of books
        Set<Book> authorBooks = new HashSet<>();
        if(author!=null){
            return authorBooks = bookRepository.findBooksByAuthorId(authorId);
        }else{
           return authorBooks;
        }
    }


    public ResponseDto getBookByIdAndAuthorId(String bookId,String authorId){
        ResponseDto responseDto = new ResponseDto();
        // find by id
        Book book = bookRepository.findBookByIdAndAuthorId(bookId,authorId);
        if(book!=null){
            responseDto.setMessage(book.getTitle()+" Book is found");
            responseDto.setStatus(HttpStatus.OK);
            responseDto.setObj(book);
            responseDto.setSuccess(true);
        }else{
            responseDto.setMessage("Failed to find Book");
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setObj(null);
        }
        return responseDto;
    }

    public ResponseDto updateBookByIdAndAuthorId(String bookId,String authorId,Book bookObj){
        ResponseDto responseDto = new ResponseDto();
        // find by id
        Book book = bookRepository.findBookByIdAndAuthorId(bookId,authorId);
        if(book!=null){

            book.setTitle(bookObj.getTitle());
            book.setIsbn(bookObj.getIsbn());
            book.setPrice(bookObj.getPrice());
            book.setCategory(bookObj.getCategory());
            bookRepository.save(book);
            responseDto.setMessage("Book is successfully Updated");
            responseDto.setStatus(HttpStatus.OK);
            responseDto.setObj(book);
            responseDto.setSuccess(true);
        }else{
            responseDto.setMessage("Failed to update Book");
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setObj(null);
        }
        return responseDto;
    }


    public String deleteBookByIdAndAuthorId(String bookId,String authorId){
        ResponseDto responseDto = new ResponseDto();
        // find by id
        Book book = bookRepository.findBookByIdAndAuthorId(bookId,authorId);
        if(book!=null){
            bookRepository.delete(book);
            return "Book is successfully deleted";
        }else{
            return "Failed to delete a book";
        }

    }

}
