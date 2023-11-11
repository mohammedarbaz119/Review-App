package com.example.movieapp.services;

import com.example.movieapp.User;
import com.example.movieapp.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userrepo;


}
