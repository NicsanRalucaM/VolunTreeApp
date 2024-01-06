package com.example.repository;

import com.example.entity.VolunteerApplicant;
import com.example.entity.VolunteerProgram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolunteerApplicantRepository extends JpaRepository<VolunteerApplicant, Long> {
    List<VolunteerApplicant> findByUserId(Long userId);
    List<VolunteerApplicant> findByProgramId(Long programId);
}
