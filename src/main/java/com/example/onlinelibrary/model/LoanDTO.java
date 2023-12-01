package com.example.onlinelibrary.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanDTO {
    private Long id;
    private Long bookId;
    private Book book;
    private Long userId;
    private String loanDate;
    private String returnDate;

    public LoanDTO(Book book, String returnDate) {
        this.returnDate = returnDate;
        this.book = book;
    }

    public LoanDTO(Loan loan) {
        this.bookId = loan.getBookId();
        this.userId = loan.getUserId();
        this.loanDate = loan.getLoanDate();
        this.returnDate = loan.getReturnDate();
    }
}
