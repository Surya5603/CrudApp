package com.example.CRUDapp;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();
    private Long userIdCounter = 1L;

    public List<User> GetAllUsers() {
        return users;
    }

    public User GetUserById(Long id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    public User SaveUser(User user) {
        if (user.getId() == null) {
            user.setId(userIdCounter++);
        }
        users.add(user);
        return user;
    }

    public void DeleteUser(Long id) {
        users.removeIf(user -> user.getId().equals(id));
    }
}
