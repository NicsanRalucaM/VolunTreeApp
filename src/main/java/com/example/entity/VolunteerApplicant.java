package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "volunteer_applicants")
public class VolunteerApplicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "UserId should not be null!")
    private int userId;

    @NotNull(message = "ProgramId should not be null!")
    private int programId;

    @NotNull(message = "ApplyDate should not be null!")
    private LocalDateTime applyDate;
}
