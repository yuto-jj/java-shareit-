package ru.practicum.user;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public List<UserDto> getUsers() {
        return repository.getUsers().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(long id) {
        Optional<User> user = repository.getUser(id);
        if (user.isPresent()) {
            return UserMapper.toUserDto(user.get());
        } else {
            throw new NotFoundException("User not found");
        }
    }

    @Override
    public UserDto saveUser(UserDto user) {
        validationForNewUser(user);
        return UserMapper.toUserDto(repository.save(UserMapper.toUser(user)));
    }

    @Override
    public UserDto updateUser(UserDto user) {
        validationForUpdateUser(user);
        User user1 = UserMapper.toUser(user);
        user1.setId(user.getId());
        return UserMapper.toUserDto(repository.update(user1));
    }

    @Override
    public void deleteUser(long id) {
        repository.delete(id);
    }

    private void validationForNewUser (UserDto user) {
        if (repository.containsEmail(user.getEmail())) {
            throw new ConflictException("The user with this email already exists.");
        }
    }

    private void validationForUpdateUser(UserDto user) {
        Optional<User> oldUser = repository.getUser(user.getId());
        if (oldUser.isPresent()) {
            if (user.getEmail() != null) {
                if (!user.getEmail().isEmpty() && user.getEmail().contains("@")) {
                    if (repository.containsEmail(user.getEmail()) &&
                            !oldUser.get().getEmail().equals(user.getEmail())) {
                        throw new ConflictException("The user with this email already exists.");
                    }
                } else {
                    throw new ValidationException("Email should be valid and contain '@'.");
                }
            }
        } else {
            throw new NotFoundException("User not found");
        }
    }
}