package com.example.CRUDapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> GetAllUsers() {
        logger.info("Fetching all users");
        List<User> users = userService.GetAllUsers();
        logger.debug("Users fetched: {}", users);
        return userService.GetAllUsers();
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> GetUserById(@PathVariable Long id) {
        logger.info("Fetching user with ID: {}", id);
        User user = userService.GetUserById(id);
        if (user == null) {
            logger.warn("User with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
        logger.debug("User fetched: {}", user);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("Creating new user: {}", user);
        User createdUser = userService.SaveUser(user);
        logger.debug("User created: {}", createdUser);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> UpdateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User u = userService.GetUserById(id);
        if (u != null) {
            u.setName(updatedUser.getName());
            u.setAge(updatedUser.getAge());
            return ResponseEntity.ok(userService.SaveUser(u));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeleteUser(@PathVariable Long id) {
        if (userService.GetUserById(id) != null) {
            userService.DeleteUser(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
