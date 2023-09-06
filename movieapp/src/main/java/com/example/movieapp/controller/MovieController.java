package com.example.movieapp.controller;

import com.example.movieapp.Movie;
import com.example.movieapp.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/movies")
public class MovieController {
    @Autowired
    private MovieService service;
    @GetMapping
    public List<Movie> all(){
        return service.getall();
    }
}
