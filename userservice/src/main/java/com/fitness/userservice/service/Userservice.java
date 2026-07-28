package com.fitness.userservice.service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.Userrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Userservice {

    @Autowired
    private Userrepo userrepo;

    public UserResponse register(RegisterRequest request) {
        if(userrepo.existsByEmail(request.getEmail())){
            throw  new RuntimeException("it already exists");
        }
        User user=new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFristName(request.getFristName());
        user.setLastName(request.getLastName());
        User saveduser=  userrepo.save(user);

        UserResponse userResponse=new UserResponse();
        userResponse.setId(saveduser.getId());
        userResponse.setPassword(saveduser.getPassword());
        userResponse.setEmail(saveduser.getEmail());
        userResponse.setFristName(saveduser.getFristName());
        userResponse.setLastName(saveduser.getLastName());
        userResponse.setCreatedAt(saveduser.getCreatedAt());
        userResponse.setUpdatedAt(saveduser.getUpdatedAt());
        return userResponse;
    }

    public UserResponse getUserProfile(String userId) {
        User user = userrepo.findById(userId)
                .orElseThrow(()->new RuntimeException("user not found "));
        UserResponse userResponse=new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setPassword(user.getPassword());
        userResponse.setEmail(user.getEmail());
        userResponse.setFristName(user.getFristName());
        userResponse.setLastName(user.getLastName());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());
        return userResponse;
    }

    public Boolean validateUser(String userId) {
        return userrepo.existsById(userId);
    }
}
