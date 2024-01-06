package com.example.repository;

import com.example.entity.VolunteerProgram;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VolunteerProgramRepositoryTest {

    @Autowired
    private VolunteerProgramRepository volunteerProgramRepository;

    @Test
    void testSaveVolunteerProgram() {
        VolunteerProgram program = new VolunteerProgram(null, 1L, "Program Title", "Program Description");
        VolunteerProgram savedProgram = volunteerProgramRepository.save(program);
        assertNotNull(savedProgram.getId());
        Optional<VolunteerProgram> retrievedProgram = volunteerProgramRepository.findById(savedProgram.getId());
        assertTrue(retrievedProgram.isPresent());
        assertEquals("Program Title", retrievedProgram.get().getTitle());
        assertEquals("Program Description", retrievedProgram.get().getDescription());
    }

    @Test
    void testDeleteVolunteerProgram() {
        VolunteerProgram program = new VolunteerProgram(null, 1L, "Program Title", "Program Description");
        VolunteerProgram savedProgram = volunteerProgramRepository.save(program);

        volunteerProgramRepository.deleteById(savedProgram.getId());
        Optional<VolunteerProgram> retrievedProgram = volunteerProgramRepository.findById(savedProgram.getId());
        assertTrue(retrievedProgram.isEmpty());
    }
}
