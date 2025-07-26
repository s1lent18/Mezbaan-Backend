package com.example.Mezbaan.service;

import com.example.Mezbaan.database.models.Users;
import com.example.Mezbaan.database.repository.UsersRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
public class UserService {

    @Autowired
    UsersRepository usersRepository;

    @Getter @Setter
    public static class UserSignInRequest {
        public String email;
        public String name;
        public String image;
        public String password;

        public UserSignInRequest(String email, String name, String image, String password) {
            this.email = email;
            this.name = name;
            this.image = image;
            this.password = password;
        }
    }

    @Getter @Setter
    public static class UserSignInResponse {
        public Integer id;
        public Timestamp createdAt;
        public String email;
        public String name;
        public String image;

        public UserSignInResponse(String email, String name, String image, Integer id, Timestamp createdAt) {
            this.email = email;
            this.name = name;
            this.image = image;
            this.createdAt = createdAt;
            this.id = id;
        }
    }

    @Transactional
    public UserSignInResponse addUser(UserSignInRequest request) {

        if (usersRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        Users user = new Users(request.email, request.image, request.name, request.password);

        Users savedUser = usersRepository.save(user);

        return new UserSignInResponse(
            savedUser.getEmail(),
            savedUser.getName(),
            savedUser.getImage(),
            savedUser.getId(),
            savedUser.getCreatedAt()
        );
    }
}
