package com.example.CRUDapp;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;



@Service
public class UserService {
    private final List<User> users = new ArrayList<>();
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
//    public List<User> GetAllUsers() {
//        return users;
//    }
//
//    public User GetUserById(Long id) {
//        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
//    }
//
//    public User SaveUser(User user) {
//        if (user.getId() == null) {
//            user.setId(userIdCounter++);
//        }
//        users.add(user);
//        return user;
//    }
private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<User> GetAllUsers() {
        logger.info("Retrieving all users from the database");
        return userRepository.findAll();
    }

    public User GetUserById(Long id) {
        logger.info("Looking up user with ID: {}", id);


        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            logger.info("User with ID {} found: {}", id, user);
            return user;
        } else {
            logger.error("User with ID {} not found", id);
            return  null;
        }
    }


    public User SaveUser(User user) {
        logger.info("Saving user to the database: {}", user);
        return userRepository.save(user);
    }

    public void DeleteUser(Long id) {
        users.removeIf(user -> user.getId().equals(id));
    }
}
