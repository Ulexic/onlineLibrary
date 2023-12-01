package com.example.onlinelibrary.service;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.Loan;
import com.example.onlinelibrary.model.LoanRequest;

public interface LoanService {
    Loan borrowBook(Book book, LoanRequest loanRequest);
}
