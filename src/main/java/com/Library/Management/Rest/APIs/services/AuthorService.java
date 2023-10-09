package com.Library.Management.Rest.APIs.services;

import com.Library.Management.Rest.APIs.dtos.AuthorDto;
import com.Library.Management.Rest.APIs.dtos.ResponseDto;
import com.Library.Management.Rest.APIs.exception.LibraryManagementException;
import com.Library.Management.Rest.APIs.exception.ResourceNotFoundException;
import com.Library.Management.Rest.APIs.models.Author;
import com.Library.Management.Rest.APIs.repositories.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

   @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ModelMapper modelMapper;




    public ResponseDto saveAuthor(AuthorDto authorDto) {
        ResponseDto responseDto = new ResponseDto();
        Author author = mapToEntity(authorDto);

        // Check if the author has an ID (existing author) or not (new author)
        if (author.getId() != null) {
            // Author with ID exists, attempt to update
            Author existingAuthor = authorRepository.findById(author.getId()).orElseThrow(()-> new ResourceNotFoundException("Author","id",author.getId()));

            if (existingAuthor != null) {
                // Update the existing author
                existingAuthor = updateExistingAuthor(existingAuthor, author);
                responseDto.setMessage("Author is updated successfully");
                responseDto.setSuccess(true);
                responseDto.setStatus(HttpStatus.OK);

                // map entity to dto
                AuthorDto updatedDto = mapToDto(existingAuthor);
                responseDto.setObj(updatedDto);
            } else {
                responseDto.setMessage("Author with the provided ID does not exist, We cannot perform update");
                responseDto.setSuccess(false);
                responseDto.setStatus(HttpStatus.BAD_REQUEST);
                responseDto.setObj("Author with the provided ID does not exist, We cannot perform update");
            }
        } else {
            // Author with no ID provided, create a new author
            Author newAuthor = authorRepository.save(author);
            responseDto.setMessage("Author is successfully created");
            responseDto.setSuccess(true);
            responseDto.setStatus(HttpStatus.CREATED);
            // map entity to dto
            AuthorDto savedDto = mapToDto(newAuthor);
            responseDto.setObj(savedDto);
        }

        return responseDto;
    }

    private Author mapToEntity(AuthorDto authorDto){
        Author author = modelMapper.map(authorDto,Author.class);
        return author;
    }

    private AuthorDto mapToDto(Author author){
        AuthorDto authorDto =  modelMapper.map(author,AuthorDto.class);
        return authorDto;
    }


    private Author updateExistingAuthor(Author existingAuthor,Author updatedAuthor){
       existingAuthor.setAge(updatedAuthor.getAge());
       existingAuthor.setName(updatedAuthor.getName());
       existingAuthor.setAddress(updatedAuthor.getAddress());
       authorRepository.save(existingAuthor);
       return existingAuthor;
   }

   public ResponseDto findAll(){
    ResponseDto responseDto = new ResponseDto();
    List<Author> authors = authorRepository.findAll();
    // map list of author entities to dtos
    List<AuthorDto> authorDtos = authors.stream().map(author -> mapToDto(author)).collect(Collectors.toList());
    responseDto.setObj(authorDtos);
    responseDto.setSuccess(true);
    responseDto.setMessage("List of all authors");
    responseDto.setStatus(HttpStatus.OK);
    return responseDto;
   }


    public ResponseDto getAllAuthors(int pageNo, int pageSize, String sortBy,String sortDir) {
        ResponseDto responseDto = new ResponseDto();
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        authorRepository.findAll(pageable);

        // building Dto
        responseDto.setSuccess(true);
        responseDto.setStatus(HttpStatus.OK);
        responseDto.setMessage("List of all Authors in page format");
        // conver list to dtos
        Page<AuthorDto> authorDtos = authorRepository.findAll(pageable).map(author -> mapToDto(author));
        responseDto.setObj(authorDtos);

        return responseDto;
    }

    public ResponseDto getAuthorById(String id){
        ResponseDto responseDto = new ResponseDto();
        Author author = authorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Author","id",id));
        if(author!=null){
            responseDto.setStatus(HttpStatus.OK);
            responseDto.setSuccess(true);
            responseDto.setMessage(String.format("Author %s was found",author.getName(),id));
            // map entity to dto
            AuthorDto authorDto = mapToDto(author);
            responseDto.setObj(authorDto);
        }else{
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setSuccess(false);
            responseDto.setMessage(String.format("Author was not found",id));
            responseDto.setObj(null);
        }
        return responseDto;
    }

    public ResponseDto deleteAuthor(String id){
        ResponseDto responseDto = new ResponseDto();

        // find author by id
        Author author = authorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Author","id",id));

        if(author!=null){
            authorRepository.delete(author);
            responseDto.setMessage("Author is successfully Deleted");
            responseDto.setStatus(HttpStatus.OK);
            responseDto.setObj("Author is successfully Deleted");
            responseDto.setSuccess(true);
            return responseDto;
        }else{
            responseDto.setMessage("Failed to delete Author");
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setObj("Deleting author failed");
            responseDto.setSuccess(false);
          return responseDto;
        }

    }



}
