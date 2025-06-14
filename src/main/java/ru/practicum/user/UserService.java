package ru.practicum.user;

import java.util.List;

interface UserService {

    List<UserDto> getUsers();

    UserDto getUser(long id);

    UserDto saveUser(UserDto user);

    UserDto updateUser(UserDto user);

    void deleteUser(long id);
}