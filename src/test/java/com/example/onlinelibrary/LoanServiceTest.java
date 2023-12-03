package com.example.onlinelibrary;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.Loan;
import com.example.onlinelibrary.model.LoanDTO;
import com.example.onlinelibrary.model.User;
import com.example.onlinelibrary.repository.BookRepository;
import com.example.onlinelibrary.repository.LoanRepository;
import com.example.onlinelibrary.repository.UserRepository;
import com.example.onlinelibrary.service.LoanService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class LoanServiceTest {
    @Resource
    private LoanRepository loanRepository;
    @Resource
    private BookRepository bookRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private LoanService service;

    private Long userId;

    @BeforeEach
    public void setup() {
        User user = new User(1L, "t", "#", "E");
        user = userRepository.save(user);
        userId = user.getId();
    }

    @Test
    void testBorrowBook() {
        loanRepository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 3);
        LoanDTO loanDTO = new LoanDTO(new Loan(1L, 1L, userId, "2021-01-01", "2021-01-01"));

        Loan loan = service.borrowBook(book, loanDTO);
        assertNotNull(loan);
    }

    @Test
    void testBorrowBookInvalidReturnDate() {
        loanRepository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 3);
        LoanDTO loanDTO = new LoanDTO(new Loan(1L, 1L, userId, "2021-01-01", "201-01-01"));

        assertThrows(IllegalArgumentException.class, () -> service.borrowBook(book, loanDTO));
    }

    @Test
    void testBorrowBookNoAvailableCopies() {
        loanRepository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 0);
        LoanDTO loanDTO = new LoanDTO(new Loan(1L, 1L, userId, "2021-01-01", "2021-01-01"));

        assertThrows(IllegalArgumentException.class, () -> service.borrowBook(book, loanDTO));
    }

    @Test
    void testBorrowBookMoreThan3Books() {
        loanRepository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 9);
        LoanDTO loanDTO = new LoanDTO(new Loan(1L, 1L, userId, "2021-01-01", "2021-01-01"));

        service.borrowBook(book, loanDTO);
        service.borrowBook(book, loanDTO);
        service.borrowBook(book, loanDTO);

        assertThrows(IllegalArgumentException.class, () -> service.borrowBook(book, loanDTO));
    }

    @Test
    void testGetBorrowedBooks() {
        loanRepository.deleteAll();
        bookRepository.deleteAll();
        Book book = new Book(1L, "Title", "Author test", "2021-01-01", 3);
        LoanDTO loanDTO = new LoanDTO(new Loan(1L, 1L, userId, "2021-01-01", "2021-01-01"));

        service.borrowBook(book, loanDTO);
        List<LoanDTO> loans = service.getBorrowedBooks(userId);
        assertEquals(1, loans.size());
    }

    @Test
    void testGetBorrowedBooksNoLoans() {
        loanRepository.deleteAll();
        assertThrows(IllegalArgumentException.class, () -> service.getBorrowedBooks(1L));
    }
}
