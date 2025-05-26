package ru.practicum.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private final Map<Long, ArrayList<Item>> items = new HashMap<>();

    @Override
    public List<Item> findByUserId(long userId) {
        return new ArrayList<>(items.get(userId));
    }

    @Override
    public Item save(long userId, Item item) {
        item.setId(getId());
        items.get(userId).add(item);
        return item;
    }

    @Override
    public void deleteByUserIdAndItemId(long userId, long itemId) {
        items.get(userId).removeIf(item -> item.getId() == itemId);
    }

    private long getId() {
        long currentMaxId = items.values().stream()
                .flatMap(List::stream)
                .mapToLong(Item::getId)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
