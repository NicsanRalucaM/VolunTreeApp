package com.example.controller;

import com.example.entity.Organization;
import com.example.entity.User;
import com.example.entity.UserOrganization;
import com.example.service.OrganizationService;
import com.example.service.UserOrganizationService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user-organization")
public class UserOrganizationController {

    @Autowired
    private UserOrganizationService userOrganizationService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserOrganization>> getAllUserOrganizations() {
        List<UserOrganization> userOrganizations = userOrganizationService.getAllUserOrganizations();
        return new ResponseEntity<>(userOrganizations, HttpStatus.OK);
    }

    @GetMapping("/{userOrganizationId}")
    public ResponseEntity<UserOrganization> getUserOrganizationById(@PathVariable Long userOrganizationId) {
        Optional<UserOrganization> userOrganization = userOrganizationService.getUserOrganizationById(userOrganizationId);
        return userOrganization.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<UserOrganization> addUserToOrganization(
            @RequestParam Long userId,
            @RequestParam Long organizationId) {

        User user = userService.getUserById(userId).orElse(null);
        Organization organization = organizationService.getOrganizationById(organizationId).orElse(null);

        if (user == null || organization == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserOrganization newUserOrganization = userOrganizationService.addUserToOrganization(user, organization);
        if (newUserOrganization != null) {
            return new ResponseEntity<>(newUserOrganization, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{userOrganizationId}")
    public ResponseEntity<Void> removeUserFromOrganization(@PathVariable Long userOrganizationId) {
        userOrganizationService.removeUserFromOrganization(userOrganizationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/organizations/{organizationId}")
    public ResponseEntity<Void> deleteUserOrganizationsByOrganizationId(@PathVariable Long organizationId) {
        userOrganizationService.deleteUserOrganizationsByOrganizationId(organizationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
