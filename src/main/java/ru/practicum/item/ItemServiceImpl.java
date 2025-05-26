package ru.practicum.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    public List<Item> getItems(long userId) {
        return itemRepository.findByUserId(userId);
    }

    public Item addNewItem(long userId, Item item) {
        return itemRepository.save(userId, item);
    }

    public void deleteItem(long userId, long itemId) {
        itemRepository.deleteByUserIdAndItemId(userId, itemId);
    }
}
