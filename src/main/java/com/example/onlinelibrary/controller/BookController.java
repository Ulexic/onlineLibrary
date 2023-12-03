package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.Loan;
import com.example.onlinelibrary.model.LoanDTO;
import com.example.onlinelibrary.service.BookService;
import com.example.onlinelibrary.service.LoanService;
import jakarta.ws.rs.QueryParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@RestController()
@RequestMapping("books")
public class BookController {

    private final BookService bookService;
    private final LoanService loanService;

    // The BookService and LoanService instances will be injected by Spring
    public BookController(BookService bookService, LoanService loanService) {
        this.bookService = bookService;
        this.loanService = loanService;
    }

    // Add a book to the database
    @PostMapping("/")
    public ResponseEntity<Book> addBook(@RequestBody Book body){
        try {
            Book book = bookService.addBook(body);
            return new ResponseEntity<>(book, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format");
        }
    }

    // Get all books from the database
    @GetMapping("/")
    public ResponseEntity<List<Book>> getBooks() {
        try {
            List<Book> books = bookService.findAll();
            return new ResponseEntity<>(books, HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Get a book by its id
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        try {
            Book book = bookService.findById(id);
            return new ResponseEntity<>(book, HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Get all book with specified title
    @GetMapping("/title")
    public ResponseEntity<List<Book>> getBookByTitle(@QueryParam("title") String title  ) {
        try {
            title = title.replace("+", " ");
            List<Book> books = bookService.findAllByTitle(title);
            return new ResponseEntity<>(books, HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Get all books by an author
    @GetMapping("/author")
    public ResponseEntity<List<Book>> getBookByAuthor(@QueryParam("author") String author) {
        try {
            author = author.replace("+", " ");
            List<Book> books = bookService.findAllByAuthor(author);
            return new ResponseEntity<>(books, HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Add a loan to the database (borrow a book) and reduce the number of available copies
    @PostMapping("/borrow/{id}")
    public ResponseEntity<Loan> borrowBook(@PathVariable Long id, @RequestBody LoanDTO body) {
        try {

            Book book = bookService.findById(id);
            Loan loan = loanService.borrowBook(book, body);
            return new ResponseEntity<>(loan, HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // Get all available books
    @GetMapping("/available")
    public ResponseEntity<List<Book>> getAvailableBooks() {
        try {
            List<Book> books = bookService.findAllAvailable();
            return new ResponseEntity<>(books, HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
