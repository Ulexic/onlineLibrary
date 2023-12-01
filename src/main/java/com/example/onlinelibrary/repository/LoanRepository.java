package com.example.onlinelibrary.repository;

import com.example.onlinelibrary.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long>{
    List<Loan> findAllByUserId(Long userId);
}
