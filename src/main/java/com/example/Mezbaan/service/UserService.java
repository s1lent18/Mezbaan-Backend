package com.example.Mezbaan.service;

import com.example.Mezbaan.database.models.Status;
import com.example.Mezbaan.database.models.Users;
import com.example.Mezbaan.database.repository.UsersRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Getter @Setter
    public static class UserSignUpRequest {
        public String email;
        public String name;
        public String image;
        public String password;

        public UserSignUpRequest(String email, String name, String image, String password) {
            this.email = email;
            this.name = name;
            this.image = image;
            this.password = password;
        }
    }

    @Getter @Setter
    public static class UserSignInRequest {
        public String email;
        public String password;

        public UserSignInRequest(String email, String password) {
            this.email = email;
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
        public String token;

        public UserSignInResponse(String email, String name, String image, Integer id, Timestamp createdAt) {
            this.email = email;
            this.name = name;
            this.image = image;
            this.createdAt = createdAt;
            this.id = id;
        }
    }

    @Transactional
    public UserSignInResponse addUser(UserSignUpRequest request) {

        if (usersRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        Users user = new Users(request.email, request.image, request.name, request.password);

        Users savedUser = usersRepository.save(user);

        savedUser.setPassword(passwordEncoder.encode(savedUser.getPassword()));

        usersRepository.save(savedUser);

        return new UserSignInResponse(
            savedUser.getEmail(),
            savedUser.getName(),
            savedUser.getImage(),
            savedUser.getId(),
            savedUser.getCreatedAt()
        );
    }

    public UserSignInResponse getUser(String email) {
        Users user = usersRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not Found"));

        return new UserSignInResponse(
                user.getEmail(),
                user.getName(),
                user.getImage(),
                user.getId(),
                user.getCreatedAt()
        );
    }

    public void saveUser(Users user) {
        user.setStatus(Status.ONLINE);
        usersRepository.save(user);
    }

    public void disconnect(Users user) {
        var storedUser = usersRepository.findById(user.getId()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
            usersRepository.save(storedUser);
        }
    }

    public List<Users> findConnectedUsers() {
        return usersRepository.findAllByStatus(Status.ONLINE);
    }
}
