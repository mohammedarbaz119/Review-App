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
import java.util.logging.Logger;

@Service
public class MovieService {
    Logger logger = Logger.getLogger(MovieService.class.getName());
    @Autowired
    private MovieRepository movieRepository;
    public List<Movie> getall(){
        logger.info("this a messgae");
        logger.info(SecurityContextHolder.getContext().toString());

        return  movieRepository.findAll();
    }
    public Movie getmovie(ObjectId objid){

        return movieRepository.findById(objid).orElse(null);
    }
}
