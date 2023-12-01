package com.example.onlinelibrary.controller;

// TODO: test

import com.example.onlinelibrary.model.UserDTO;
import com.example.onlinelibrary.service.UserService;
import com.example.onlinelibrary.service.implementation.BookServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController()
@RequestMapping("/")
public class UserController {

    private UserService service;
    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO body) {
        try {
            UserDTO user = service.register(body);
            if (user == null)
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User could not be created");

            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch(IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDTO) {
        UserDTO user = service.login(userDTO);


        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        UserDTO user = service.update(userDTO, id);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        UserDTO user = service.get(id);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) {
        UserDTO user = service.delete(id);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }
}
