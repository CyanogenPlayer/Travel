package dev.cyan.travel.controller;

import dev.cyan.travel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hotels")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    //TODO write methods
}
