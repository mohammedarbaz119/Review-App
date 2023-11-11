package com.example.movieapp.controller;

import com.example.movieapp.Movie;
import com.example.movieapp.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/movies")
public class MovieController {
    @Autowired
    private MovieService service;


    @GetMapping("/{imdbId}")
    public ResponseEntity<Movie> GetSingleMovie(@PathVariable String imdbId){
        Optional<Movie> movie = service.getmovie(imdbId);
        return movie.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> ResponseEntity.notFound().build());
    }



    @GetMapping
    public List<Movie> all(){
        return service.getall();
    }
}
