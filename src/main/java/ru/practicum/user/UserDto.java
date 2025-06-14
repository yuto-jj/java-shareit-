package ru.practicum.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    @NotNull
    @NotEmpty(message = "Email cannot be empty.")
    @Email(message = "Email should be valid and contain '@'.")
    private String email;
    private String name;
}
