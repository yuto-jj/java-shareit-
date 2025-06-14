package ru.practicum.item;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class ItemDto {
    private Long id;
    private Long userId;
    @NotNull
    @NotEmpty(message = "Name cannot be empty.")
    private String name;
    @NotNull
    @NotEmpty(message = "Description cannot be empty.")
    private String description;
    @Getter
    @NotNull
    private Boolean available;
}
