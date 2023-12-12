package com.Library.Management.Rest.APIs.services;

import com.Library.Management.Rest.APIs.dtos.ResponseDto;
import com.Library.Management.Rest.APIs.dtos.requests.AuthorDtoRequest;
import com.Library.Management.Rest.APIs.dtos.requests.BookDtoRequest;
import com.Library.Management.Rest.APIs.dtos.responses.AuthorDtoResponse;
import com.Library.Management.Rest.APIs.dtos.responses.BookDtoResponse;
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


    public ResponseDto saveBook(BookDtoRequest bookDtoRequest, String authorId){
        ResponseDto responseDto = new ResponseDto();

        // convert dto to entity
        Book book = mapToEntity(bookDtoRequest);

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
               BookDtoResponse existingBookDtoResponse = mapToDtoResponse(existingBook);
               responseDto.setObj(existingBookDtoResponse);
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
            // convert from entity to dto
            BookDtoResponse newBookDtoResponse = mapToDtoResponse(newBook);
            responseDto.setObj(newBookDtoResponse);
        }



        return responseDto;
    }


    private Book mapToEntity(BookDtoRequest bookDtoRequest){
        Book book = modelMapper.map(bookDtoRequest,Book.class);
        return book;
    }

    private Book mapToEntityResponse(BookDtoResponse bookDtoResponse){
        Book book = modelMapper.map(bookDtoResponse,Book.class);
        return book;
    }

    private BookDtoRequest mapToDto(Book book){
        BookDtoRequest bookDtoRequest =  modelMapper.map(book,BookDtoRequest.class);
        return bookDtoRequest;
    }

    private BookDtoResponse mapToDtoResponse(Book book){
        BookDtoResponse bookDtoResponse =  modelMapper.map(book,BookDtoResponse.class);
        return bookDtoResponse;
    }



    private Author mapToEntity(AuthorDtoRequest authorDtoRequest){
        Author author = modelMapper.map(authorDtoRequest,Author.class);
        return author;
    }

    private Author mapToEntityResponse(AuthorDtoResponse authorDtoResponse){
        Author author = modelMapper.map(authorDtoResponse,Author.class);
        return author;
    }

    private AuthorDtoRequest mapToDto(Author author){
        AuthorDtoRequest authorDtoRequest =  modelMapper.map(author,AuthorDtoRequest.class);
        return authorDtoRequest;
    }

    private AuthorDtoResponse mapToDtoResponse(Author author){
        AuthorDtoResponse authorDtoResponse =  modelMapper.map(author,AuthorDtoResponse.class);
        return authorDtoResponse;
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
            Set<AuthorDtoResponse> authorDtoResponses = authorBooks.stream().map(auth-> mapToDtoResponse(author)).collect(Collectors.toSet());
             responseDto.setObj(authorDtoResponses);
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
            BookDtoResponse bookDtoResponse = mapToDtoResponse(book);
            responseDto.setObj(bookDtoResponse);
            responseDto.setSuccess(true);
        }else{
            responseDto.setMessage("Failed to find Book");
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setObj(null);
            responseDto.setSuccess(false);
        }
        return responseDto;
    }

    public ResponseDto updateBookByIdAndAuthorId(String bookId,String authorId,BookDtoRequest bookDtoObjRequest){
        ResponseDto responseDto = new ResponseDto();

        // converty dto to entity
        Book bookEntity = mapToEntity(bookDtoObjRequest);

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
            BookDtoResponse updatedBookDtoResponse = mapToDtoResponse(book);
            responseDto.setObj(updatedBookDtoResponse);
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
