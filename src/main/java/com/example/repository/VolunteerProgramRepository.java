package com.example.repository;

import com.example.entity.SocialPost;
import com.example.entity.VolunteerProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerProgramRepository extends JpaRepository<VolunteerProgram, Long> {
    List<VolunteerProgram> findByOrganizationId(Long ogranizationId);

}

