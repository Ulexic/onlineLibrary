// TODO: Test
// TODO: Better paths
package com.example.onlinelibrary.controller;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.Loan;
import com.example.onlinelibrary.model.LoanRequest;
import com.example.onlinelibrary.service.BookService;
import com.example.onlinelibrary.service.LoanService;
import jakarta.ws.rs.QueryParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController()
@RequestMapping("books")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;
    private final LoanService loanService;

    // The BookService and LoanService instances will be injected by Spring
    public BookController(BookService bookService, LoanService loanService) {
        this.bookService = bookService;
        this.loanService = loanService;
    }

    @PostMapping("/")
    public ResponseEntity<Book> addBook(@RequestBody Book body){
        try {
            Book book = bookService.addBook(body);
            return new ResponseEntity<>(book, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books = bookService.findAll();

        if (books.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No books found");
        }
        return new ResponseEntity<>(books, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.findById(id);

        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        return new ResponseEntity<>(book, HttpStatus.ACCEPTED);
    }

    @GetMapping("/title")
    public ResponseEntity<List<Book>> getBookByTitle(@QueryParam("title") String title) {
        title = title.replace("+", " ");
        List<Book> books = bookService.findAllByTitleIgnoreCase(title);

        if (books.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No books found");
        }
        return new ResponseEntity<>(books, HttpStatus.ACCEPTED);
    }

    @GetMapping("/author")
    public ResponseEntity<List<Book>> getBookByAuthor(@QueryParam("author") String author) {
        author = author.replace("+", " ");
        List<Book> books = bookService.findAllByAuthorIgnoreCase(author);

        if (books.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No books found");
        }
        return new ResponseEntity<>(books, HttpStatus.ACCEPTED);
    }

    @PostMapping("/borrow/{id}")
    public ResponseEntity<Loan> borrowBook(@PathVariable Long id, @RequestBody LoanRequest body) {
        Book book = bookService.findById(id);
        if (book == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");

        try {
            Loan loan = loanService.borrowBook(book, body);
            return new ResponseEntity<>(loan, HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/available")
    public ResponseEntity<List<Book>> getAvailableBooks() {
        List<Book> books = bookService.findAllAvailable();

        if (books.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No books found");

        return new ResponseEntity<>(books, HttpStatus.ACCEPTED);
    }
}
