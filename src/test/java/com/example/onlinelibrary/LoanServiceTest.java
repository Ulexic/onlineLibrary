package com.example.onlinelibrary;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.Loan;
import com.example.onlinelibrary.model.LoanDTO;
import com.example.onlinelibrary.repository.BookRepository;
import com.example.onlinelibrary.repository.LoanRepository;
import com.example.onlinelibrary.service.BookService;
import com.example.onlinelibrary.service.LoanService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class LoanServiceTest {
    @Resource
    private LoanRepository repository;
    @Resource
    private LoanService service;

    @Test
    void testBorrowBook() {
        repository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 3);
        LoanDTO loanDTO = new LoanDTO(new Loan(1L, 1L, 1L, "2021-01-01", "2021-01-01"));

        Loan loan = service.borrowBook(book, loanDTO);
        assertNotNull(loan);
    }

    @Test
    void testBorrowBookInvalidReturnDate() {
        repository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 3);
        LoanDTO loanDTO = new LoanDTO(new Loan(1L, 1L, 1L, "2021-01-01", "201-01-01"));

        assertThrows(IllegalArgumentException.class, () -> {service.borrowBook(book, loanDTO);});
    }

    @Test
    void testBorrowBookNoAvailableCopies() {
        repository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 0);
        LoanDTO loanDTO = new LoanDTO(new Loan(1L, 1L, 1L, "2021-01-01", "2021-01-01"));

        assertThrows(IllegalArgumentException.class, () -> {service.borrowBook(book, loanDTO);});
    }

    @Test
    void testBorrowBookMoreThan3Books() {
        repository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 9);
        LoanDTO loanDTO = new LoanDTO(new Loan(1L, 1L, 1L, "2021-01-01", "2021-01-01"));

        service.borrowBook(book, loanDTO);
        service.borrowBook(book, loanDTO);
        service.borrowBook(book, loanDTO);

        assertThrows(IllegalArgumentException.class, () -> {service.borrowBook(book, loanDTO);});
    }

    @Test
    void testGetBorrowedBooks() {
        repository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 3);
        LoanDTO loanDTO = new LoanDTO(new Loan(1L, 1L, 1L, "2021-01-01", "2021-01-01"));

        service.borrowBook(book, loanDTO);
        List<LoanDTO> loans = service.getBorrowedBooks(1L);
        assertEquals(1, loans.size());
    }

    @Test
    void testGetBorrowedBooksNoLoans() {
        repository.deleteAll();
        List<LoanDTO> loans = service.getBorrowedBooks(1L);
        assertEquals(0, loans.size());
    }

    @Test
    void testGetBorrowedBooksWrongUserId() {
        repository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 3);
        LoanDTO loanDTO = new LoanDTO(new Loan(1L, 1L, 1L, "2021-01-01", "2021-01-01"));

        service.borrowBook(book, loanDTO);
        List<LoanDTO> loans = service.getBorrowedBooks(2L);
        assertEquals(0, loans.size());
    }
}
