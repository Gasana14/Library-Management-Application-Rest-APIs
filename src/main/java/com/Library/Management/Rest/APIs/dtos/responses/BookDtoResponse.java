package com.Library.Management.Rest.APIs.dtos.responses;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDtoResponse {

    private String id;
    @NotEmpty
    @NotNull
    @Size(min = 2, message = "Book Title should not be less than 2 characters")
    @Size(max = 30, message = "Book Title should not exceed 30 characters")
    private String title;
    @NotEmpty
    @NotNull
    private String isbn;
    private float price;
    @NotEmpty
    @NotNull
    @Size(min = 2,message = "Book Category should not be less than 2 characters")
    private String category;
    private String authorName;

}
