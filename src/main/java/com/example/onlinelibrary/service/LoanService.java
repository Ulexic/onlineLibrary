package com.example.onlinelibrary.service;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.Loan;
import com.example.onlinelibrary.model.LoanDTO;

import java.util.List;

public interface LoanService {
    Loan borrowBook(Book book, LoanDTO loanDTO);
    List<LoanDTO> getBorrowedBooks(Long userId);
}
