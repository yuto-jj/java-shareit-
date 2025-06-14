package ru.practicum.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") long id) {
        return userService.getUser(id);
    }

    @PostMapping
    public UserDto saveNewUser(@Valid @RequestBody UserDto user) {
        return userService.saveUser(user);
    }

    @PatchMapping("/{id}")
    public UserDto updateUser(@PathVariable("id") long id, @RequestBody UserDto user) {
        user.setId(id);
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
    }
}