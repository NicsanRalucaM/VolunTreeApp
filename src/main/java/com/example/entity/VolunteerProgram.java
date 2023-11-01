
package com.example.entity;

import lombok.*;
import jakarta.persistence.*;
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "volunteer_program")
public class VolunteerProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long organizationId;
    private String title;
    private String description;
}