package com.example.model;

import com.example.entity.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class AllUsers {

    private List<User> users = new ArrayList<User>();
    private String error = "";
    private int statusCode = 500;

}