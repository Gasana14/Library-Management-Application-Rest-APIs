package com.Library.Management.Rest.APIs.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(
        description = "AuthorDto Model Information"
)
public class AuthorDto {

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
    Set<BookDto> books;
}
