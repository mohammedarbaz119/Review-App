package com.example.movieapp.controller;

import com.example.movieapp.DTO.JWTtransfer;
import com.example.movieapp.JwtUtil;
import com.example.movieapp.Role;
import com.example.movieapp.User;
import com.example.movieapp.repos.UserRepository;
import com.example.movieapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/user")
public class UserControler {

    @Value("${SECRET}")
    String sec;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService serv;
    @Autowired
    private UserRepository repo;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody User user){
        if(user.getUsername() == null || user.getPassword() == null){
            return new ResponseEntity<>("username or password cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if(repo.findByUsername(user.getUsername()).isPresent()){
            return new ResponseEntity<>("user with this name already exists",HttpStatus.CONFLICT);
        }
        String encodedpass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedpass);
        user.setRole(Role.USER);
        user.setReviews(new ArrayList<>());
        repo.save(user);
        return new ResponseEntity<>(new JWTtransfer(jwtUtil.generateToken(user.getUsername()),user.getUsername()),HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody User user){
        if(user.getUsername() == null || user.getPassword() == null){
            return new ResponseEntity<>("username or password cannot be empty", HttpStatus.BAD_REQUEST);
        }
        User user1 = repo.findByUsername(user.getUsername()).orElse(null);
        if(user1==null){
            return new ResponseEntity<>("username is incorrect",HttpStatus.CONFLICT);
        }
        if(!passwordEncoder.matches(user.getPassword(), user1.getPassword())){
            return new ResponseEntity<>("password incorrect",HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new JWTtransfer(jwtUtil.generateToken(user.getUsername()),user1.getUsername()),HttpStatus.OK);
    }

}
