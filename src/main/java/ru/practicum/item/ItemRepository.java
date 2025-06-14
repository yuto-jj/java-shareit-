package ru.practicum.item;

import java.util.List;

public interface ItemRepository {

    List<Item> findByUserId(long userId);

    Item getItem(long itemId);

    Item save(long userId, Item item);

    Item update(long userId, Item item);

    void deleteByUserIdAndItemId(long userId, long itemId);

    List<Item> getAllItems();
}