package com.Library.Management.Rest.APIs.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "AuthorDto Request Model Information"
)
public class AuthorDtoResponse {

    private String id;
    @Schema(
            description = "Author full name"
    )
    @NotEmpty
    @NotNull
    @Size(min = 2,message = "Author's name should have atleast 2 characters")
    private String name;

    @Schema(
            description = "Author age"
    )
    @NotNull
    private Integer age;

    @Schema(
            description = "Author Location Address"
    )
    @NotEmpty
    @NotNull
    @Size(max = 100,message = "Address should not exceed 100 characters")
    private String address;

    @Schema(
            description = "Set of Books"
    )
    Set<BookDtoResponse> books;



    public void setBooks(Set<BookDtoResponse> books) {
        this.books = books;
    }

    public Set<BookDtoResponse> setBookAuthorName() {
        Set<BookDtoResponse> bookDtoResponses = new HashSet<>();

        if (books != null) {
            for (BookDtoResponse book : books) {
                book.setAuthorName(name); // Set the authorName for each book
                bookDtoResponses.add(book);
            }
        }

        return bookDtoResponses;
    }

}
