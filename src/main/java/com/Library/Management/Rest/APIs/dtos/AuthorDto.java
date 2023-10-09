package com.Library.Management.Rest.APIs.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {

    private String id;
    @NotEmpty
    @NotNull
    @Size(min = 2,message = "Author's name should have atleast 2 characters")
    private String name;
    @NotNull
    private Integer age;
    @NotEmpty
    @NotNull
    @Size(max = 100,message = "Address should not exceed 100 characters")
    private String address;
    Set<BookDto> books;
}
