package com.example.movieapp.controller;

import com.example.movieapp.DTO.ReviewRequest;
import com.example.movieapp.User;
import com.example.movieapp.repos.MovieRepository;
import com.example.movieapp.repos.UserRepository;
import com.example.movieapp.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/review")
public class Reviewcontroller {
    Logger logger = Logger.getLogger(Reviewcontroller.class.getName());

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewService reviewService;
    @PostMapping("/create")
    @Secured("USER")
    public  ResponseEntity<?> CreateReview(@RequestBody ReviewRequest reviewRequest){

            String user = SecurityContextHolder.getContext().getAuthentication().getName();
            if (reviewRequest.getImdbId() == null || reviewRequest.getBody() == null) {
                return ResponseEntity.badRequest().body("fields cannot be empty");
            }
            if(movieRepository.findByImdbId(reviewRequest.getImdbId()).isEmpty()){
                return ResponseEntity.notFound().build();
            }
            boolean b=  reviewService.createReview( reviewRequest.getBody(),reviewRequest.getImdbId(), user);
            if(b) {
                return ResponseEntity.ok("review created");
            }else{
            return new ResponseEntity<>("some error happened while creating review",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/delete")
    @Secured("USER")
    public  ResponseEntity<?> DeleteReview(@RequestBody ReviewRequest reviewRequest) {

        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info(user);
        if (reviewRequest.getImdbId() == null || reviewRequest.getBody() == null) {
            return ResponseEntity.badRequest().body("fields cannot be empty");
        }
        if (movieRepository.findByImdbId(reviewRequest.getImdbId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        reviewService.deleteReviewFromUserAndMovie(user, reviewRequest.getImdbId());
        return ResponseEntity.ok("review created");
    }
    @PostMapping("/edit")
    @Secured("USER")
    public  ResponseEntity<?> EditReview(@RequestBody ReviewRequest reviewRequest) {

        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info(user);
        if (reviewRequest.getImdbId() == null || reviewRequest.getBody() == null) {
            return ResponseEntity.badRequest().body("fields cannot be empty");
        }
        if (movieRepository.findByImdbId(reviewRequest.getImdbId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        reviewService.EditReviewFromUserAndMovie(user, reviewRequest);
        return ResponseEntity.ok("review edited");
    }



    }
