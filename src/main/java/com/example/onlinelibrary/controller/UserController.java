package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.LoanDTO;
import com.example.onlinelibrary.model.UserDTO;
import com.example.onlinelibrary.service.LoanService;
import com.example.onlinelibrary.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController()
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final LoanService loanService;

    public UserController(UserService userService, LoanService loanService) {
        this.userService = userService;
        this.loanService = loanService;
    }

    // Add a user to the database
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO body) {
        try {
            UserDTO user = userService.register(body);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch(IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // Login a user
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDTO) {
        try {
            UserDTO user = userService.login(userDTO);
            return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());

        }
    }

    // Update a user
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        try {
            UserDTO user = userService.update(userDTO, id);
            return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Get a user information
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        try {
            UserDTO user = userService.get(id);
            return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
        try {
            userService.delete(id);
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Get all books borrowed by a user
    @GetMapping("/{id}/borrowed")
    public ResponseEntity<List<LoanDTO>> getBorrowedBooks(@PathVariable Long id) {
        try {
            List<LoanDTO> book = loanService.getBorrowedBooks(id);
            return new ResponseEntity<>(book, HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
