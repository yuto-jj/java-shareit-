package ru.practicum.item;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private final Map<Long, ArrayList<Item>> items = new HashMap<>();

    @Override
    public List<Item> findByUserId(long userId) {
        return new ArrayList<>(items.get(userId));
    }

    @Override
    public Item getItem(long itemId) {
        return items.values()
                .stream()
                .flatMap(List::stream)
                .filter(item -> item.getId().equals(itemId))
                .findFirst().orElse(null);
    }

    @Override
    public Item save(long userId, Item item) {
        item.setUserId(userId);
        item.setId(getId());
        if (items.containsKey(userId)) {
            items.get(userId).add(item);
        } else {
            ArrayList<Item> itemsArray = new ArrayList<>();
            itemsArray.add(item);
            items.put(userId, itemsArray);
        }
        return item;
    }

    @Override
    public Item update(long userId, Item item) {
        Item oldItem = items.entrySet().stream()
                .filter(e -> e.getKey().equals(userId))
                .flatMap(e -> e.getValue().stream())
                .filter(item1 -> item.getId().equals(item.getId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "Вещь с id " + item.getId() + " у пользователя " + userId + " не найдена"
                ));
        oldItem.setName(item.getName());
        oldItem.setDescription(item.getDescription());
        oldItem.setAvailable(item.getAvailable());
        return oldItem;
    }

    @Override
    public void deleteByUserIdAndItemId(long userId, long itemId) {
        items.get(userId).removeIf(item -> item.getId() == itemId);
    }

    @Override
    public List<Item> getAllItems() {
        return items.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
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
