package ru.practicum.item;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class Item {
    private Long id;
    private Long userId;
    private String name;
    private String description;
    @Getter
    private Boolean available;
}