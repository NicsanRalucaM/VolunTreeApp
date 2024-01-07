package com.example.controller;

import com.example.controller.OrganizationController;
import com.example.entity.Organization;
import com.example.entity.User;
import com.example.service.OrganizationService;
import com.example.service.UserOrganizationService;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrganizationControllerTest {

    @InjectMocks
    private OrganizationController organizationController;

    @Mock
    private OrganizationService organizationService;

    @Mock
    private UserService userService;

    @Mock
    private UserOrganizationService userOrganizationService;

    @Test
    void getAllOrganizations() {
        List<Organization> mockOrganizations = Arrays.asList(new Organization(), new Organization());
        when(organizationService.getAllOrganizations()).thenReturn(mockOrganizations);
        ResponseEntity<List<Organization>> responseEntity = organizationController.getAllOrganizations();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockOrganizations, responseEntity.getBody());
    }

    @Test
    void getOrganizationById_ExistingOrganization() {
        Long organizationId = 1L;
        Organization mockOrganization = new Organization();
        when(organizationService.getOrganizationById(organizationId)).thenReturn(Optional.of(mockOrganization));

        ResponseEntity<Organization> responseEntity = organizationController.getOrganizationById(organizationId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockOrganization, responseEntity.getBody());
    }

    @Test
    void getOrganizationById_NonexistentOrganization() {
        Long organizationId = 1L;
        when(organizationService.getOrganizationById(organizationId)).thenReturn(Optional.empty());

        ResponseEntity<Organization> responseEntity = organizationController.getOrganizationById(organizationId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void getOrganizationByName_ExistingOrganization() {
        String organizationName = "TestOrganization";
        Organization mockOrganization = new Organization();
        when(organizationService.getOrganizationByName(organizationName)).thenReturn(Optional.of(mockOrganization));

        ResponseEntity<Organization> responseEntity = organizationController.getOrganizationByName(organizationName);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockOrganization, responseEntity.getBody());
    }

    @Test
    void getOrganizationByName_NonexistentOrganization() {
        String organizationName = "NonexistentOrganization";
        when(organizationService.getOrganizationByName(organizationName)).thenReturn(Optional.empty());

        ResponseEntity<Organization> responseEntity = organizationController.getOrganizationByName(organizationName);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void addOrganization_ValidRequest() {
        Organization inputOrganization = new Organization();
        Long userId = 1L;
        User mockUser = new User();
        when(userService.getUserById(userId)).thenReturn(Optional.of(mockUser));
        when(userOrganizationService.isUserInAnyOrganization(userId)).thenReturn(false);

        Organization mockSavedOrganization = new Organization();
        when(organizationService.addOrganization(inputOrganization, mockUser)).thenReturn(mockSavedOrganization);

        ResponseEntity<Organization> responseEntity = organizationController.addOrganization(inputOrganization, userId);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(mockSavedOrganization, responseEntity.getBody());
    }

    @Test
    void addOrganization_InvalidUser() {
        Organization inputOrganization = new Organization();
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        ResponseEntity<Organization> responseEntity = organizationController.addOrganization(inputOrganization, userId);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void addOrganization_UserInOrganization() {
        Organization inputOrganization = new Organization();
        Long userId = 1L;
        User mockUser = new User();
        when(userService.getUserById(userId)).thenReturn(Optional.of(mockUser));
        when(userOrganizationService.isUserInAnyOrganization(userId)).thenReturn(true);

        ResponseEntity<Organization> responseEntity = organizationController.addOrganization(inputOrganization, userId);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void updateOrganization_ExistingOrganization() {
        Long organizationId = 1L;
        Organization updatedOrganization = new Organization();
        Organization mockUpdatedOrganization = new Organization();
        when(organizationService.updateOrganization(organizationId, updatedOrganization)).thenReturn(Optional.of(mockUpdatedOrganization));

        ResponseEntity<Organization> responseEntity = organizationController.updateOrganization(organizationId, updatedOrganization);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockUpdatedOrganization, responseEntity.getBody());
    }

    @Test
    void updateOrganization_NonexistentOrganization() {
        Long organizationId = 1L;
        Organization updatedOrganization = new Organization();
        when(organizationService.updateOrganization(organizationId, updatedOrganization)).thenReturn(Optional.empty());

        ResponseEntity<Organization> responseEntity = organizationController.updateOrganization(organizationId, updatedOrganization);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void deleteOrganization() {
        Long organizationId = 1L;

        ResponseEntity<Void> responseEntity = organizationController.deleteOrganization(organizationId);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(organizationService, times(1)).deleteOrganization(organizationId);
    }
}

