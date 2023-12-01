package com.example.onlinelibrary.service.implementation;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.Loan;
import com.example.onlinelibrary.model.LoanRequest;
import com.example.onlinelibrary.repository.LoanRepository;
import com.example.onlinelibrary.service.BookService;
import com.example.onlinelibrary.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class LoanServiceImpl implements LoanService {

    private static final Logger log = LoggerFactory.getLogger(LoanServiceImpl.class);
    private final LoanRepository repository;
    private final BookService bookService;

    public LoanServiceImpl(LoanRepository repository, BookService bookService) {
        this.repository = repository;
        this.bookService = bookService;
    }

    @Override
    public Loan borrowBook(Book book, LoanRequest loanRequest) {
        bookService.borrowBook(book, loanRequest);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Loan loan = new Loan();
        loan.setBookId(book.getId());
        loan.setUserId(loanRequest.getUserId());
        loan.setLoanDate(formatter.format(date));
        loan.setReturnDate(loanRequest.getReturnDate());

        return repository.save(loan);
    }
}
