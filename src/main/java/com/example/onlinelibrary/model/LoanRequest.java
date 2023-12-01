package com.example.onlinelibrary.model;

import lombok.Getter;

@Getter
public class LoanRequest {

    private Long id;
    private Long bookId;
    private Long userId;
    private String loanDate;
    private String returnDate;
}
