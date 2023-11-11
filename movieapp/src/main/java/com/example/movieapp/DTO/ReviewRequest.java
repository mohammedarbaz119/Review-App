package com.example.movieapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewRequest {
    private String imdbId;
    private String body;
}
