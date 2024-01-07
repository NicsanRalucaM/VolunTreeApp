package com.example.controller;

import com.example.entity.VolunteerApplicant;
import com.example.service.VolunteerApplicantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VolunteerApplicantControllerTest {

    @InjectMocks
    private VolunteerApplicantController volunteerApplicantController;

    @Mock
    private VolunteerApplicantService volunteerApplicantService;

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void getAllVolunteerApplicants() {
        VolunteerApplicant applicant = new VolunteerApplicant(1, 1, 1, LocalDateTime.now());

        List<VolunteerApplicant> applicants = Arrays.asList(applicant);

        when(volunteerApplicantService.getAllVolunteerApplicants()).thenReturn(applicants);

        ResponseEntity<List<VolunteerApplicant>> responseEntity = volunteerApplicantController.getAllVolunteerApplicants();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(applicants, responseEntity.getBody());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void getVolunteerApplicantsById_ExistingApplicant() {
        Long applicantId = 1L;
        VolunteerApplicant mockApplicant = new VolunteerApplicant(1, 1, 1, LocalDateTime.now());

        when(volunteerApplicantService.getVolunteerApplicantById(applicantId)).thenReturn(Optional.of(mockApplicant));

        ResponseEntity<VolunteerApplicant> responseEntity = volunteerApplicantController.getVolunteerApplicantsById(applicantId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockApplicant, responseEntity.getBody());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void getVolunteerApplicantsById_NonexistentApplicant() {
        Long applicantId = 1L;

        when(volunteerApplicantService.getVolunteerApplicantById(applicantId)).thenReturn(Optional.empty());

        ResponseEntity<VolunteerApplicant> responseEntity = volunteerApplicantController.getVolunteerApplicantsById(applicantId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void getAllVolunteerApplicantsByUserId() {
        Integer userId = 1;
        VolunteerApplicant applicant = new VolunteerApplicant(1, userId, 1, LocalDateTime.now());

        List<VolunteerApplicant> applicants = Arrays.asList(applicant);

        when(volunteerApplicantService.getVolunteerApplicantsByUserId(Long.valueOf(userId))).thenReturn(applicants);

        ResponseEntity<List<VolunteerApplicant>> responseEntity = volunteerApplicantController.getAllVolunteerApplicantsByUserId(Long.valueOf(userId));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(applicants, responseEntity.getBody());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void getAllVolunteerApplicantsByProgramId() {
        Integer programId = 1;
        VolunteerApplicant applicant = new VolunteerApplicant(1, 1, programId, LocalDateTime.now());

        List<VolunteerApplicant> applicants = Arrays.asList(applicant);

        when(volunteerApplicantService.getVolunteerApplicantsByProgramId(Long.valueOf(programId))).thenReturn(applicants);

        ResponseEntity<List<VolunteerApplicant>> responseEntity = volunteerApplicantController.getAllVolunteerApplicantsByProgramId(Long.valueOf(programId));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(applicants, responseEntity.getBody());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void addVolunteerApplicant() {
        VolunteerApplicant applicantToAdd = new VolunteerApplicant(1, 1, 1, LocalDateTime.now());

        when(volunteerApplicantService.addVolunteerApplicant(applicantToAdd)).thenReturn(applicantToAdd);

        ResponseEntity<VolunteerApplicant> responseEntity = volunteerApplicantController.addVolunteerApplicant(applicantToAdd);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(applicantToAdd, responseEntity.getBody());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void deleteVolunteerApplicant() {
        Long applicantId = 1L;

        ResponseEntity<Void> responseEntity = volunteerApplicantController.deleteVolunteerApplicant(applicantId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(volunteerApplicantService, times(1)).deleteVolunteerApplicant(applicantId);
    }

}
