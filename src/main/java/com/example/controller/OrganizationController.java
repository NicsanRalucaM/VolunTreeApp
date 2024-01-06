package com.example.controller;

import com.example.entity.Organization;
import com.example.entity.User;
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
@RequestMapping("/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserOrganizationService userOrganizationService;

    @GetMapping
    public ResponseEntity<List<Organization>> getAllOrganizations() {
        List<Organization> organizations = organizationService.getAllOrganizations();
        return new ResponseEntity<>(organizations, HttpStatus.OK);
    }

    @GetMapping("/{organizationId}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable Long organizationId) {
        Optional<Organization> organization = organizationService.getOrganizationById(organizationId);
        return organization.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/name/{organizationName}")
    public ResponseEntity<Organization> getOrganizationByName(@PathVariable String organizationName) {
        Optional<Organization> organization = organizationService.getOrganizationByName(organizationName);
        return organization.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public ResponseEntity<Organization> addOrganization(@RequestBody Organization organization, @RequestParam Long userId) {
        User user = userService.getUserById(userId).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (userOrganizationService.isUserInAnyOrganization(userId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Organization newOrganization = organizationService.addOrganization(organization, user);
        return new ResponseEntity<>(newOrganization, HttpStatus.CREATED);
    }


    @PutMapping("/{organizationId}")
    public ResponseEntity<Organization> updateOrganization(@PathVariable Long organizationId, @RequestBody Organization updatedOrganization) {
        Optional<Organization> organization = organizationService.updateOrganization(organizationId, updatedOrganization);
        return organization.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{organizationId}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long organizationId) {
        organizationService.deleteOrganization(organizationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

