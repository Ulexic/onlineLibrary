package com.example.onlinelibrary.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue
    private Long id;

    private Long bookId;
    private Long userId;
    private String loanDate;
    private String returnDate;
}
