package com.example.controller;

import com.example.entity.VolunteerProgram;
import com.example.service.VolunteerProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/volunteerprograms")
public class VolunteerProgramController {

    @Autowired
    private VolunteerProgramService volunteerProgramService;

    @GetMapping
    public ResponseEntity<List<VolunteerProgram>> getAllVolunteerPrograms() {
        List<VolunteerProgram> volunteerPrograms = volunteerProgramService.getAllVolunteerPrograms();
        return new ResponseEntity<>(volunteerPrograms, HttpStatus.OK);
    }

    @GetMapping("/{programId}")
    public ResponseEntity<VolunteerProgram> getVolunteerProgramById(@PathVariable Long programId) {
        Optional<VolunteerProgram> volunteerProgram = volunteerProgramService.getVolunteerProgramById(programId);
        return volunteerProgram.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<VolunteerProgram> addVolunteerProgram(@RequestBody VolunteerProgram volunteerProgram) {
        VolunteerProgram newVolunteerProgram = volunteerProgramService.addVolunteerProgram(volunteerProgram);
        return new ResponseEntity<>(newVolunteerProgram, HttpStatus.CREATED);
    }

    @PutMapping("/{programId}")
    public ResponseEntity<VolunteerProgram> updateVolunteerProgram(@PathVariable Long programId, @RequestBody VolunteerProgram updatedVolunteerProgram) {
        Optional<VolunteerProgram> volunteerProgram = volunteerProgramService.updateVolunteerProgram(programId, updatedVolunteerProgram);
        return volunteerProgram.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{programId}")
    public ResponseEntity<Void> deleteVolunteerProgram(@PathVariable Long programId) {
        volunteerProgramService.deleteVolunteerProgram(programId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
