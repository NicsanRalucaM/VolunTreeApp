package com.example.model;


import com.example.entity.User;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class UserCreated {

    private User user = new User();
    private String error = "";
    private int statusCode = 500;

}