package com.fitness.userservice.controller;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private Userservice userservice;
    @GetMapping("/{userid}")
    public ResponseEntity<UserResponse>getUserProfile(@PathVariable String userid){
        return ResponseEntity.ok(userservice.getUserProfile(userid));
    }
    @PostMapping("/register")
    public ResponseEntity<UserResponse>register(@Validated @RequestBody RegisterRequest request){
    return ResponseEntity.ok(userservice.register(request));
    }
    @GetMapping("/{userid}/validate")
    public ResponseEntity<Boolean>validateUser(@PathVariable String userid){
        return ResponseEntity.ok(userservice.validateUser(userid));
    }
}
