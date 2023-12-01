package com.example.onlinelibrary.repository;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// The @Repository annotation is used to indicate that the class provides the mechanism for storage, retrieval,
// search, update and delete operation on objects.
@Repository
public interface LoanRepository extends JpaRepository<Loan, Long>{
    List<Loan> findAllByBookId(Long bookId);
}
