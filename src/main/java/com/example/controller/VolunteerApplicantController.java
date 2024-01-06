package com.example.controller;

import com.example.entity.VolunteerApplicant;
import com.example.entity.VolunteerProgram;
import com.example.service.VolunteerApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/volunteerapplicant")
public class VolunteerApplicantController {
    @Autowired
    private VolunteerApplicantService volunteerApplicantService;

    @GetMapping
    public ResponseEntity<List<VolunteerApplicant>> getAllVolunteerApplicants() {
        List<VolunteerApplicant> volunteerApplicants = volunteerApplicantService.getAllVolunteerApplicants();
        return new ResponseEntity<>(volunteerApplicants, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VolunteerApplicant> getVolunteerApplicantsById(@PathVariable Long id) {
        Optional<VolunteerApplicant> volunteerApplicant = volunteerApplicantService.getVolunteerApplicantById(id);
        return volunteerApplicant.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<VolunteerApplicant>> getAllVolunteerApplicantsByUserId(@PathVariable Long userId) {
        List<VolunteerApplicant> volunteerApplicants = volunteerApplicantService.getVolunteerApplicantsByUserId(userId);
        return new ResponseEntity<>(volunteerApplicants, HttpStatus.OK);
    }

    @GetMapping("/program/{programId}")
    public ResponseEntity<List<VolunteerApplicant>> getAllVolunteerApplicantsByProgramId(@PathVariable Long programId) {
        List<VolunteerApplicant> volunteerApplicants = volunteerApplicantService.getVolunteerApplicantsByProgramId(programId);
        return new ResponseEntity<>(volunteerApplicants, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VolunteerApplicant> addVolunteerApplicant(@RequestBody VolunteerApplicant volunteerApplicant) {
        VolunteerApplicant newVolunteerApplicant = volunteerApplicantService.addVolunteerApplicant(volunteerApplicant);
        return new ResponseEntity<>(newVolunteerApplicant, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolunteerApplicant(@PathVariable Long id) {
        volunteerApplicantService.deleteVolunteerApplicant(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
