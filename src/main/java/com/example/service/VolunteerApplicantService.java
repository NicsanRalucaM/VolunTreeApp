package com.example.service;

import com.example.entity.VolunteerApplicant;
import com.example.entity.VolunteerProgram;
import com.example.repository.VolunteerApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VolunteerApplicantService {
    @Autowired
    private VolunteerApplicantRepository volunteerApplicantRepository;

    public List<VolunteerApplicant> getAllVolunteerApplicants(){
        return volunteerApplicantRepository.findAll();
    }

    public Optional<VolunteerApplicant> getVolunteerApplicantById(Long id) {
        return volunteerApplicantRepository.findById(id);
    }

    public List<VolunteerApplicant> getVolunteerApplicantsByUserId(Long userId) {
        return volunteerApplicantRepository.findByUserId(userId);
    }

    public List<VolunteerApplicant> getVolunteerApplicantsByProgramId(Long programId){
        return volunteerApplicantRepository.findByProgramId(programId);
    }

    public VolunteerApplicant addVolunteerApplicant(VolunteerApplicant volunteerApplicant) {
        return volunteerApplicantRepository.save(volunteerApplicant);
    }

    public void deleteVolunteerApplicant(Long programId) {
        volunteerApplicantRepository.deleteById(programId);
    }
}
