package com.example.controller;

import com.example.controller.VolunteerProgramController;
import com.example.entity.VolunteerProgram;
import com.example.service.VolunteerProgramService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VolunteerProgramControllerTest {

    @InjectMocks
    private VolunteerProgramController volunteerProgramController;

    @Mock
    private VolunteerProgramService volunteerProgramService;

    @Test
    void getAllVolunteerPrograms() {
        VolunteerProgram program1 = new VolunteerProgram(1L, 1L, "Program 1", "Description 1");
        VolunteerProgram program2 = new VolunteerProgram(2L, 1L, "Program 2", "Description 2");

        when(volunteerProgramService.getAllVolunteerPrograms()).thenReturn(Arrays.asList(program1, program2));

        ResponseEntity<List<VolunteerProgram>> responseEntity = volunteerProgramController.getAllVolunteerPrograms();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Arrays.asList(program1, program2), responseEntity.getBody());
    }

    @Test
    void getVolunteerProgramById_ExistingProgram() {
        Long programId = 1L;
        VolunteerProgram mockProgram = new VolunteerProgram(programId, 1L, "Program 1", "Description 1");

        when(volunteerProgramService.getVolunteerProgramById(programId)).thenReturn(Optional.of(mockProgram));

        ResponseEntity<VolunteerProgram> responseEntity = volunteerProgramController.getVolunteerProgramById(programId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockProgram, responseEntity.getBody());
    }

    @Test
    void getVolunteerProgramById_NotFound() {
        Long programId = 1L;
        when(volunteerProgramService.getVolunteerProgramById(programId)).thenReturn(Optional.empty());

        ResponseEntity<VolunteerProgram> responseEntity = volunteerProgramController.getVolunteerProgramById(programId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void getVolunteerProgramByOrganizationId() {
        VolunteerProgram program1 = new VolunteerProgram(1L, 1L, "Program 1", "Description 1");
        VolunteerProgram program2 = new VolunteerProgram(2L, 1L, "Program 2", "Description 2");

        when(volunteerProgramService.getVolunteerProgramByOrganizationId(1L)).thenReturn(Arrays.asList(program1, program2));

        ResponseEntity<List<VolunteerProgram>> responseEntity = volunteerProgramController.getVolunteerProgramByOrganizationId(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Arrays.asList(program1, program2), responseEntity.getBody());
    }

    @Test
    void addVolunteerProgram() {
        VolunteerProgram programToAdd = new VolunteerProgram(1L, 1L, "Program 1", "Description 1");

        when(volunteerProgramService.addVolunteerProgram(any(VolunteerProgram.class))).thenReturn(programToAdd);

        ResponseEntity<VolunteerProgram> responseEntity = volunteerProgramController.addVolunteerProgram(programToAdd);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(programToAdd, responseEntity.getBody());
    }

    @Test
    void updateVolunteerProgram_ExistingProgram() {
        Long programId = 1L;
        VolunteerProgram updatedProgram = new VolunteerProgram(programId, 1L, "Updated Program", "Updated Description");

        when(volunteerProgramService.updateVolunteerProgram(programId, updatedProgram)).thenReturn(Optional.of(updatedProgram));

        ResponseEntity<VolunteerProgram> responseEntity = volunteerProgramController.updateVolunteerProgram(programId, updatedProgram);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedProgram, responseEntity.getBody());
    }

    @Test
    void updateVolunteerProgram_NotFound() {
        Long programId = 1L;
        VolunteerProgram updatedProgram = new VolunteerProgram(programId, 1L, "Updated Program", "Updated Description");

        when(volunteerProgramService.updateVolunteerProgram(programId, updatedProgram)).thenReturn(Optional.empty());

        ResponseEntity<VolunteerProgram> responseEntity = volunteerProgramController.updateVolunteerProgram(programId, updatedProgram);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void deleteVolunteerProgram() {
        Long programId = 1L;

        ResponseEntity<Void> responseEntity = volunteerProgramController.deleteVolunteerProgram(programId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(volunteerProgramService, times(1)).deleteVolunteerProgram(programId);
    }
}
