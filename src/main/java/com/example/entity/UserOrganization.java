package com.example.entity;

import lombok.*;
import lombok.*;
import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "user_organization")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrganization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;
}

