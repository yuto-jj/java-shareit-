package ru.practicum.user;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private final Set<String> emails = new HashSet<>();

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Optional<User> getUser(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User save(User user) {
        user.setId(getId());
        emails.add(user.getEmail());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        User oldUser = users.get(user.getId());
        if (user.getName() != null) {
            oldUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            oldUser.setEmail(user.getEmail());
        }
        return oldUser;
    }

    @Override
    public void delete(long id) {
        users.remove(id);
    }

    private long getId() {
        long lastId = users.values().stream()
                .mapToLong(User::getId)
                .max()
                .orElse(0);
        return lastId + 1;
    }

    public boolean containsEmail(String email) {
        return emails.contains(email);
    }
}
