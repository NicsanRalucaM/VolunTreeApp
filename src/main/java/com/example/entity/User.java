package com.example.entity;

import lombok.*;
import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Username should not be null!")
    private String name;
    @NotNull(message = "Password should not be null!")
    private String password;
    @NotNull(message = "Email should not be null!")
    private String email;


}