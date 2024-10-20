package com.management.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    @NonNull
    @NotBlank(message = "first name cannot be blank")
    private String firstName;

    @NonNull
    @NotBlank(message = "last name cannot be blank")
    private String lastName;

    @NonNull
    @NotBlank
    @Size(min = 4, max = 20, message = "The size of the password should be from 4 characters to 20 characters.")
    private String password;

    @NonNull
    @NotBlank(message = "roles cannot be blank")
    private String roles;

    @NonNull
    @NotBlank(message = "username cannot be blank")
    private String username;
}
