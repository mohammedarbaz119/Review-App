package com.example.movieapp.services;

import com.example.movieapp.DTO.ReviewRequest;
import com.example.movieapp.Movie;
import com.example.movieapp.Reviews;
import com.example.movieapp.User;
import com.example.movieapp.repos.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    Logger logger = Logger.getLogger(ReviewService.class.getName());
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ReviewRepository reviewRepository;
    public boolean createReview(String reviewbody,String imdbId,String username){
        try {
            Reviews reviews = new Reviews(reviewbody, username,imdbId);
            List<Reviews> a = mongoTemplate.findOne(new Query(Criteria.where("username").is(username)),User.class).getReviews();

            if(!a.isEmpty()){
                boolean b = a.stream().anyMatch(s -> s.getImdbId().equals(imdbId));
                if(b){
                    logger.info("b ka chakkar hai");
                    return false;
                }
            }

            reviewRepository.save(reviews);
            Query query = new Query().addCriteria(Criteria.where("imdbId").is(imdbId));
            Update update = new Update().push("reviewIds", reviews);
            mongoTemplate.updateFirst(query, update, Movie.class);
            mongoTemplate.updateFirst(new Query(Criteria.where("username").is(username)),new Update().push("reviews",reviews), User.class);
            return true;
        }
        catch (Exception e){
            logger.info(e.getMessage());
            return false;
        }
    }
    public void deleteReviewFromUserAndMovie(String username, String imdbId) {
        // Delete the review from the user record
        Query reviewQuery = new Query(Criteria.where("imdbId").is(imdbId).and("username").is(username));
mongoTemplate.remove(reviewQuery,Reviews.class);
        Query userQuery = new Query(Criteria.where("username").is(username));
        Update userUpdate = new Update().pull("reviews", Query.query(Criteria.where("imdbId").is(imdbId)));
        mongoTemplate.updateFirst(userQuery, userUpdate, User.class);

        // Delete the review from the movie record
        Query movieQuery = new Query(Criteria.where("imdbId").is(imdbId));
        Update movieUpdate = new Update().pull("reviewIds", Query.query(Criteria.where("imdbId").is(imdbId)));
        mongoTemplate.updateFirst(movieQuery, movieUpdate, Movie.class);
    }

    public void EditReviewFromUserAndMovie(String username, ReviewRequest reviewRequest) {
        Query reviewQuery = new Query(Criteria.where("imdbId").is(reviewRequest.getImdbId()).and("username").is(username));
        Update update = new Update().set("body",reviewRequest.getBody());
logger.info(reviewRequest.getBody());
        logger.info(SecurityContextHolder.getContext().getAuthentication().getName());
        mongoTemplate.updateFirst(reviewQuery,update,Reviews.class);
        Reviews a = mongoTemplate.findOne(reviewQuery,Reviews.class);
        logger.info(a.getBody());



    }
}
