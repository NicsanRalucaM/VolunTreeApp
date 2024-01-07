package com.example.controller;

import com.example.entity.User;
import com.example.enums.Role;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@SpringJUnitConfig
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void testGetAllUsers() throws Exception {
        List<User> userList = new ArrayList<>();
        when(userService.getAllUsers()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8081/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void testGetUserById() throws Exception {
        Long userId = 1L;
        User user = new User(userId, "Alex Popescu", "password123", "alex@example.com", Role.NO_ORG);
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value("Alex Popescu"))
                .andExpect(jsonPath("$.password").value("password123"))
                .andExpect(jsonPath("$.email").value("alex@example.com"));
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void testGetUserById_NotFound() throws Exception {
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void testAddUser() throws Exception {
        User user = new User(null, "Alex Popescu", "password123", "alex@example.com", Role.NO_ORG);
        when(userService.addUser(any(User.class))).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Alex Popescu\",\"password\":\"password123\",\"email\":\"alex@example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Alex Popescu"))
                .andExpect(jsonPath("$.password").value("password123"))
                .andExpect(jsonPath("$.email").value("alex@example.com"));
        verify(userService, times(1)).addUser(any(User.class));
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void testUpdateUser() throws Exception {
        Long userId = 1L;
        User existingUser = new User(userId, "Alex Popescu", "password123", "alex@example.com", Role.NO_ORG);
        User updatedUser = new User(userId, "Updated Alex Popescu", "updatedPassword", "updated@example.com", Role.NO_ORG);
        when(userService.updateUser(eq(userId), any(User.class))).thenReturn(Optional.of(updatedUser));
        mockMvc.perform(MockMvcRequestBuilders.put("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Alex Popescu\",\"password\":\"updatedPassword\",\"email\":\"updated@example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value("Updated Alex Popescu"))
                .andExpect(jsonPath("$.password").value("updatedPassword"))
                .andExpect(jsonPath("$.email").value("updated@example.com"));
        verify(userService, times(1)).updateUser(eq(userId), any(User.class));
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void testUpdateUser_NotFound() throws Exception {
        Long userId = 1L;
        when(userService.updateUser(eq(userId), any(User.class))).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.put("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Alex POpescu \",\"password\":\"updatedPassword\",\"email\":\"updated@example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        verify(userService, times(1)).updateUser(eq(userId), any(User.class));
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void testDeleteUser() throws Exception {
        Long userId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        verify(userService, times(1)).deleteUser(userId);
    }

}
