package com.example.repository;

import com.example.entity.VolunteerApplicant;
import com.example.repository.VolunteerApplicantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
public class VolunteerApplicantRepositoryTest {

    @Autowired
    private VolunteerApplicantRepository volunteerApplicantRepository;

    @Test
    void saveVolunteerApplicant() {

        VolunteerApplicant applicant = new VolunteerApplicant(1, 1, 1, LocalDateTime.now());
        VolunteerApplicant savedApplicant = volunteerApplicantRepository.save(applicant);

        assertThat(savedApplicant.getId()).isNotNull();
        assertEquals(applicant.getUserId(), savedApplicant.getUserId());
        assertEquals(applicant.getProgramId(), savedApplicant.getProgramId());
        assertEquals(applicant.getApplyDate(), savedApplicant.getApplyDate());
    }

    @Test
    void findVolunteerApplicantById() {
        VolunteerApplicant applicant = new VolunteerApplicant(1, 1, 1, LocalDateTime.now());
        volunteerApplicantRepository.save(applicant);

        Optional<VolunteerApplicant> foundApplicant = volunteerApplicantRepository.findById((long) applicant.getId());

        assertThat(foundApplicant).isPresent();
        assertEquals(applicant.getUserId(), foundApplicant.get().getUserId());
        assertEquals(applicant.getProgramId(), foundApplicant.get().getProgramId());
        assertEquals(applicant.getApplyDate(), foundApplicant.get().getApplyDate());
    }

    @Test
    void findAllVolunteerApplicants() {
        VolunteerApplicant applicant1 = new VolunteerApplicant(1, 1, 1, LocalDateTime.now());
        VolunteerApplicant applicant2 = new VolunteerApplicant(2, 2, 2, LocalDateTime.now());
        volunteerApplicantRepository.saveAll(List.of(applicant1, applicant2));

        List<VolunteerApplicant> foundApplicants = volunteerApplicantRepository.findAll();

        assertThat(foundApplicants).hasSize(2);
    }

    @Test
    void deleteVolunteerApplicant() {
        VolunteerApplicant applicant = new VolunteerApplicant(1, 1, 1, LocalDateTime.now());
        volunteerApplicantRepository.save(applicant);

        volunteerApplicantRepository.deleteById((long) applicant.getId());

        assertFalse(volunteerApplicantRepository.existsById((long) applicant.getId()));
    }

    @Test
    void findByUserId() {

        VolunteerApplicant applicant1 = new VolunteerApplicant(1, 1, 1, LocalDateTime.now());
        VolunteerApplicant applicant2 = new VolunteerApplicant(2, 1, 2, LocalDateTime.now());
        volunteerApplicantRepository.saveAll(List.of(applicant1, applicant2));

        List<VolunteerApplicant> foundApplicants = volunteerApplicantRepository.findByUserId(1L);

        assertThat(foundApplicants).hasSize(2);
    }

    @Test
    void findByProgramId() {
        VolunteerApplicant applicant1 = new VolunteerApplicant(1, 1, 1, LocalDateTime.now());
        VolunteerApplicant applicant2 = new VolunteerApplicant(2, 2, 1, LocalDateTime.now());
        volunteerApplicantRepository.saveAll(List.of(applicant1, applicant2));

        List<VolunteerApplicant> foundApplicants = volunteerApplicantRepository.findByProgramId(1L);
        assertThat(foundApplicants).hasSize(1);
    }
}
