package com.example.CRUDapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> GetAllUsers() {
        return userService.GetAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> GetUserById(@PathVariable Long id) {
        User u = userService.GetUserById(id);
        if (u != null) {
            return ResponseEntity.ok(u);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public User CreateUser(@RequestBody User user) {
        return userService.SaveUser(user);
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
