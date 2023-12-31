package com.example.onlinelibrary.service.implementation;

import com.example.onlinelibrary.model.*;
import com.example.onlinelibrary.repository.UserRepository;
import com.example.onlinelibrary.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder BCrypt = new BCryptPasswordEncoder();

    // The repository is injected into the constructor
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    // Check if the email is valid and if it's not already used
    private boolean checkEmail(String email) {
        List<User> user = repository.findAllByEmail(email);
        if(!user.isEmpty())
            return false;

        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }

    @Override
    public UserDTO login(UserDTO user) {
        List<User> users = repository.findAllByEmail(user.getEmail());

        if(users.isEmpty() || !BCrypt.matches(user.getPassword(), users.get(0).getPassword()))
            throw new IllegalArgumentException("Wrong credentials");

        return new UserDTO(users.get(0).getId());
    }

    @Override
    public UserDTO register(UserDTO request) {
        if(!checkEmail(request.getEmail())) {
            throw new IllegalArgumentException("Invalid email");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.encode(request.getPassword()));
        user = repository.save(user);
        return new UserDTO(user);
    }

    @Override
    public UserDTO update(UserDTO userDTO, Long id) {
        User user = repository.findById(id).orElse(null);
        if (user == null)
            throw new IllegalArgumentException("User not found");

        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(BCrypt.encode(userDTO.getPassword()));
        repository.save(user);

        return new UserDTO(user);
    }

    @Override
    public UserDTO get(Long id) {
        User user = repository.findById(id).orElse(null);
        if(user == null)
            throw new IllegalArgumentException("User not found");
        return new UserDTO(user);
    }

    @Override
    public void delete(Long id) {

        User user = repository.findById(id).orElse(null);
        if(user == null)
            throw new IllegalArgumentException("User not found");
        repository.delete(user);
    }
}
