package com.example.service;

import com.example.entity.VolunteerProgram;
import com.example.repository.VolunteerProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VolunteerProgramService {

    @Autowired
    private VolunteerProgramRepository volunteerProgramRepository;

    public List<VolunteerProgram> getAllVolunteerPrograms() {
        return volunteerProgramRepository.findAll();
    }

    public Optional<VolunteerProgram> getVolunteerProgramById(Long programId) {
        return volunteerProgramRepository.findById(programId);
    }

    public List<VolunteerProgram> getVolunteerProgramByOrganizationId(Long organizationId){
        return volunteerProgramRepository.findByOrganizationId(organizationId);
    }

    public VolunteerProgram addVolunteerProgram(VolunteerProgram volunteerProgram) {
        return volunteerProgramRepository.save(volunteerProgram);
    }

    public Optional<VolunteerProgram> updateVolunteerProgram(Long programId, VolunteerProgram updatedVolunteerProgram) {
        Optional<VolunteerProgram> existingProgramOptional = volunteerProgramRepository.findById(programId);
        if (existingProgramOptional.isPresent()) {
            VolunteerProgram existingProgram = existingProgramOptional.get();
            existingProgram.setOrganizationId(updatedVolunteerProgram.getOrganizationId());
            existingProgram.setTitle(updatedVolunteerProgram.getTitle());
            existingProgram.setDescription(updatedVolunteerProgram.getDescription());
            VolunteerProgram savedProgram = volunteerProgramRepository.save(existingProgram);
            return Optional.of(savedProgram);
        } else {
            return Optional.empty();
        }
    }

    public void deleteVolunteerProgram(Long programId) {
        volunteerProgramRepository.deleteById(programId);
    }
}
