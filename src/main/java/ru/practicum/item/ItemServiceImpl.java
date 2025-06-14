package ru.practicum.item;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public List<ItemDto> getItems(long userId) {
        return itemRepository.findByUserId(userId).stream().map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    public ItemDto getItem(long itemId) {
        return ItemMapper.toItemDto(itemRepository.getItem(itemId));
    }

    public ItemDto addNewItem(long userId, ItemDto item) {
        item.setUserId(userId);
        validation(item);
        return ItemMapper.toItemDto(itemRepository.save(userId, ItemMapper.toItem(item)));
    }

    public ItemDto updateItem(long userId, ItemDto item) {
        item.setUserId(userId);
        validation(item);
        return ItemMapper.toItemDto(itemRepository.update(userId, ItemMapper.toItem(item)));
    }

    public void deleteItem(long userId, long itemId) {
        itemRepository.deleteByUserIdAndItemId(userId, itemId);
    }

    public List<ItemDto> searchItems(String text) {
        return itemRepository.getAllItems().stream()
                .filter(item -> item.getAvailable() != null && item.getAvailable())
                .filter(item -> item.getName().toLowerCase().contains(text.toLowerCase()) ||
                        item.getDescription().toLowerCase().contains(text.toLowerCase()))
                .map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    private void validation(ItemDto itemDto) {
        if (userRepository.getUser(itemDto.getUserId()).isPresent()) {
            if (itemDto.getId() != null) {
                if (itemDto.getName() != null) {
                    if (itemDto.getName().isEmpty()) {
                        throw new ValidationException("The name field should not be empty.");
                    }
                }
                if (itemDto.getDescription() != null) {
                    if (itemDto.getDescription().isEmpty()) {
                        throw new ValidationException("The description field should not be empty.");
                    }
                }
            }
        } else {
            throw new NotFoundException("The user does not exist.");
        }
    }
}
