// TODO: Test
package com.example.onlinelibrary.controller;


import com.example.onlinelibrary.service.BookService;
import com.example.onlinelibrary.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("loans")
public class LoanController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private final LoanService service;

    // The LoanService instance will be injected by Spring
    public LoanController(LoanService service) {
        this.service = service;
    }


}
