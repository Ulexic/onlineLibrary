package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.LoanDTO;
import com.example.onlinelibrary.model.UserDTO;
import com.example.onlinelibrary.service.LoanService;
import com.example.onlinelibrary.service.UserService;
import com.example.onlinelibrary.service.implementation.BookServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController()
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private LoanService loanService;
    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    public UserController(UserService userService, LoanService loanService) {
        this.userService = userService;
        this.loanService = loanService;
    }

    // Add a user to the database
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO body) {
        try {
            UserDTO user = userService.register(body);
            if (user == null)
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User could not be created");

            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch(IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // Login a user
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDTO) {
        UserDTO user = userService.login(userDTO);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    // Update a user
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        UserDTO user = userService.update(userDTO, id);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    // Get a user information
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        UserDTO user = userService.get(id);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) {
        UserDTO user = userService.delete(id);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    // Get all books borrowed by a user
    @GetMapping("/{id}/borrowed")
    public ResponseEntity<List<LoanDTO>> getBorrowedBooks(@PathVariable Long id) {
        List<LoanDTO> book = loanService.getBorrowedBooks(id);
        return new ResponseEntity<>(book, HttpStatus.ACCEPTED);
    }
}
