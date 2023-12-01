package com.example.onlinelibrary.service.implementation;

import com.example.onlinelibrary.model.Book;
import com.example.onlinelibrary.model.Loan;
import com.example.onlinelibrary.model.LoanDTO;
import com.example.onlinelibrary.repository.LoanRepository;
import com.example.onlinelibrary.service.BookService;
import com.example.onlinelibrary.service.LoanService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository repository;
    private final BookService bookService;

    // The repositories are injected into the constructor
    public LoanServiceImpl(LoanRepository repository, BookService bookService) {
        this.repository = repository;
        this.bookService = bookService;
    }

    @Override
    public Loan borrowBook(Book book, LoanDTO loanDTO) {
        // The book is saved only if the publicationDate is in the format yyyy-MM-dd
        if (!loanDTO.getReturnDate().matches("^\\d{4}-\\d{2}-\\d{2}$"))
            throw new IllegalArgumentException("Invalid returnDate");

        if (book.getAvailableCopies() > 0) {
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookService.addBook(book);
            List<LoanDTO> borrowedBooks = getBorrowedBooks(loanDTO.getUserId());

            // A user can't borrow more than 3 books
            if(borrowedBooks.size() > 2)
                throw new IllegalArgumentException("You can't borrow more than 3 books");

            return add(loanDTO);
        } else {
            throw new IllegalArgumentException("No available copies");
        }
    }

    @Override
    public List<LoanDTO> getBorrowedBooks(Long userId) {
        List<Loan> loans = repository.findAllByUserId(userId).stream().filter(loan -> loan.getUserId() == userId).toList();
        List<LoanDTO> response = new ArrayList<>();

        for (Loan loan : loans) {
            response.add(new LoanDTO(bookService.findById(loan.getBookId()), loan.getReturnDate()));
        }

        return response;
    }

    private Loan add(LoanDTO loanDTO) {
        Loan loan = new Loan();
        loan.setBookId(loanDTO.getBookId());
        loan.setUserId(loanDTO.getUserId());
        loan.setLoanDate(loanDTO.getLoanDate());
        loan.setReturnDate(loanDTO.getReturnDate());

        return repository.save(loan);
    }
}
