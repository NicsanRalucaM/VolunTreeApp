package com.example.repository;

import com.example.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveUser() {
        User user = new User(null, "Alex Popescu", "password123", "alex@example.com");
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());
        Optional<User> retrievedUser = userRepository.findById(savedUser.getId());
        assertTrue(retrievedUser.isPresent());
        assertEquals("Alex Popescu", retrievedUser.get().getName());
        assertEquals("password123", retrievedUser.get().getPassword());
        assertEquals("alex@example.com", retrievedUser.get().getEmail());
    }

    @Test
    void testFindByEmail() {
        User user = new User(null, "Alex Ionescu", "password456", "alexionescu@example.com");
        userRepository.save(user);
        Optional<User> retrievedUser = userRepository.findByEmail("alexionescu@example.com");
        assertTrue(retrievedUser.isPresent());
        assertEquals("Alex Ionescu", retrievedUser.get().getName());
        assertEquals("password456", retrievedUser.get().getPassword());
    }

    @Test
    void testFindAll() {
        User user1 = new User(null, "user1 test", "password123", "user1@example.com");
        User user2 = new User(null, "user2 test", "password101", "user2@example.com");
        userRepository.saveAll(List.of(user1, user2));

        List<User> allUsers = userRepository.findAll();
        assertNotNull(allUsers);
        assertEquals(2, allUsers.size());
    }

    @Test
    void testDeleteUser() {
        User user = new User(null, "Delete Me", "delete123", "delete@example.com");
        userRepository.save(user);

        userRepository.delete(user);

        Optional<User> deletedUser = userRepository.findById(user.getId());
        assertTrue(deletedUser.isEmpty());
    }

}
