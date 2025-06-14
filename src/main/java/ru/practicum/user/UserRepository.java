package ru.practicum.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> getUsers();

    Optional<User> getUser(long id);

    User save(User user);

    User update(User user);

    void delete(long id);

    boolean containsEmail(String email);
}