package ru.practicum.item;

import java.util.List;

public interface ItemService {
    List<ItemDto> getItems(long itemId);

    ItemDto getItem(long userId);

    ItemDto addNewItem(long userId, ItemDto item);

    ItemDto updateItem(long itemId, ItemDto item);

    void deleteItem(long userId, long itemId);

    List<ItemDto> searchItems(String text);
}
