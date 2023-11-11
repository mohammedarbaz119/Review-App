package com.example.movieapp.services;

import com.example.movieapp.Movie;
import com.example.movieapp.repos.MovieRepository;
import lombok.extern.java.Log;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class MovieService {
    Logger logger = Logger.getLogger(MovieService.class.getName());
    @Autowired
    private MovieRepository movieRepository;
    public List<Movie> getall(){
        logger.info("this is a message");
        return  movieRepository.findAll();
    }
    public Optional<Movie> getmovie(String imdbId){
        return movieRepository.findByImdbId(imdbId);
    }
}
