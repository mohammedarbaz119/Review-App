package com.example.movieapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTtransfer {
    private String token;
    private String username;
}
