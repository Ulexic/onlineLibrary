package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController()
@RequestMapping("books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    // TODO: add error messages
    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody Book body){
        try {
            Book book = service.addBook(body);
            return new ResponseEntity<>(book, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books = service.findAll();
        return new ResponseEntity<>(books, HttpStatus.ACCEPTED);
    }
}
