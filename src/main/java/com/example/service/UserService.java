package com.example.service;

import com.example.entity.User;
import com.example.model.AllUsers;
import com.example.model.SingleUser;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AllUsers getAllUsers() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            return new AllUsers(new ArrayList<>(), "No users found", 204);
        }
        return new AllUsers(users, "Users found", 200);
    }


    public SingleUser getUserById(long id) {
        var result = userRepository.findById(id);

        if (result.isEmpty()) {
            return new SingleUser(null, "User not found", 404);
        }
        SingleUser user = new SingleUser(result.get(), "", 200);
        return user;
    }

    public SingleUser createUser(User user) {
        SingleUser userAdded = new SingleUser();

        if (userRepository.existsById(user.getId())) {
            userAdded.setError("User already exists");
            userAdded.setStatusCode(403);
            return userAdded;
        } else {
            userRepository.save(user);
            userAdded.setUser(user);
            userAdded.setStatusCode(200);
            return userAdded;
        }
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

}
