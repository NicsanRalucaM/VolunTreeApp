package com.example.repository;

import com.example.entity.VolunteerProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerProgramRepository extends JpaRepository<VolunteerProgram, Long> {
}

