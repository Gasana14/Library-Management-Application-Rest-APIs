package com.Library.Management.Rest.APIs.services;

import com.Library.Management.Rest.APIs.dtos.AuthorDto;
import com.Library.Management.Rest.APIs.dtos.BookDto;
import com.Library.Management.Rest.APIs.dtos.ResponseDto;
import com.Library.Management.Rest.APIs.exception.LibraryManagementException;
import com.Library.Management.Rest.APIs.exception.ResourceNotFoundException;
import com.Library.Management.Rest.APIs.models.Author;
import com.Library.Management.Rest.APIs.models.Book;
import com.Library.Management.Rest.APIs.repositories.AuthorRepository;
import com.Library.Management.Rest.APIs.repositories.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ModelMapper modelMapper;


    public ResponseDto saveBook(BookDto bookDto, String authorId){
        ResponseDto responseDto = new ResponseDto();

        // convert dto to entity
        Book book = mapToEntity(bookDto);

        // find author to assign to a specific book
        Author author = authorRepository.findById(authorId).orElseThrow(()-> new ResourceNotFoundException("Author","id",authorId));

        // check if the book id is null or not
        if(book.getId()!=null){
            // check if book entity exist in our database
            Book existingBook = bookRepository.findById(book.getId()).orElseThrow(()-> new ResourceNotFoundException("Book","id",book.getId()));

            // check if book entity exist in our database
            if(existingBook!=null){
               existingBook = updateExistingBook(existingBook,book,author);

               // convert from entity to dto
                BookDto existingBookDto = mapToDto(existingBook);
               responseDto.setObj(existingBookDto);
               responseDto.setStatus(HttpStatus.OK);
               responseDto.setSuccess(true);
               responseDto.setMessage("Book successfully Updated");
            }else{
                responseDto.setObj("Book failed to be updated");
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


    private Book mapToEntity(BookDto bookDto){
        Book book = modelMapper.map(bookDto,Book.class);
        return book;
    }

    private BookDto mapToDto(Book book){
        BookDto bookDto =  modelMapper.map(book,BookDto.class);
        return bookDto;
    }



    private Author mapToEntity(AuthorDto authorDto){
        Author author = modelMapper.map(authorDto,Author.class);
        return author;
    }

    private AuthorDto mapToDto(Author author){
        AuthorDto authorDto =  modelMapper.map(author,AuthorDto.class);
        return authorDto;
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

    public ResponseDto getBooksByAuthorId(String authorId){
        ResponseDto responseDto = new ResponseDto();
        // get author by id
        Author author = authorRepository.findById(authorId).orElseThrow(()-> new ResourceNotFoundException("Author","id",authorId));



        // set of books
        Set<Book> authorBooks = new HashSet<>();
        if(author!=null){
             authorBooks = bookRepository.findBooksByAuthorId(authorId);
             // convert entity to dto
            Set<AuthorDto> authorDtos = authorBooks.stream().map(auth-> mapToDto(author)).collect(Collectors.toSet());
             responseDto.setObj(authorDtos);
             responseDto.setSuccess(true);
             responseDto.setStatus(HttpStatus.OK);
             responseDto.setMessage("Book was found");
            return responseDto;
        }else{
            responseDto.setObj(null);
            responseDto.setSuccess(false);
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setMessage("Book was found");
            return responseDto;
        }
    }


    public ResponseDto getBookByIdAndAuthorId(String bookId,String authorId){
        ResponseDto responseDto = new ResponseDto();
        // find by id
        Book book = bookRepository.findBookByIdAndAuthorId(bookId,authorId);
        if(book!=null){
            responseDto.setMessage(book.getTitle()+" Book is found");
            responseDto.setStatus(HttpStatus.OK);

            // convert entity to dto
            BookDto bookDto = mapToDto(book);
            responseDto.setObj(bookDto);
            responseDto.setSuccess(true);
        }else{
            responseDto.setMessage("Failed to find Book");
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setObj(null);
            responseDto.setSuccess(false);
        }
        return responseDto;
    }

    public ResponseDto updateBookByIdAndAuthorId(String bookId,String authorId,BookDto bookDtoObj){
        ResponseDto responseDto = new ResponseDto();

        // converty dto to entity
        Book bookEntity = mapToEntity(bookDtoObj);

        // find by id
        Book book = bookRepository.findBookByIdAndAuthorId(bookId,authorId);
        if(book!=null){

            book.setTitle(bookEntity.getTitle());
            book.setIsbn(bookEntity.getIsbn());
            book.setPrice(bookEntity.getPrice());
            book.setCategory(bookEntity.getCategory());
            bookRepository.save(book);
            responseDto.setMessage("Book is successfully Updated");
            responseDto.setStatus(HttpStatus.OK);
            // convert entity to dto
            BookDto updatedBookDto = mapToDto(book);
            responseDto.setObj(updatedBookDto);
            responseDto.setSuccess(true);
        }else{
            responseDto.setMessage("Failed to update Book");
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setObj("Bad Request, book was not updated");
            responseDto.setSuccess(false);
        }
        return responseDto;
    }


    public ResponseDto deleteBookByIdAndAuthorId(String bookId,String authorId){
        ResponseDto responseDto = new ResponseDto();
        // find by id
        Book book = bookRepository.findBookByIdAndAuthorId(bookId,authorId);
        if(book!=null){
            bookRepository.delete(book);
            responseDto.setObj("Book is deleted successfully");
            responseDto.setSuccess(true);
            responseDto.setMessage("Book was found and deleted successfully");
            responseDto.setStatus(HttpStatus.OK);
            return responseDto;
        }else{
            responseDto.setObj("Failed to delete a book");
            responseDto.setSuccess(false);
            responseDto.setMessage("Failed to find a book, Failed to delete a book");
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            return responseDto;
        }

    }

}
