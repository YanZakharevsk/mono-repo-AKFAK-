package com.example.trocd.producer.controller;

import com.example.trocd.producer.dto.TransactionRequest;
import com.example.trocd.producer.dto.TransactionResponse;
import com.example.trocd.producer.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransactionRequest request) {
        TransactionResponse response = transactionService.processTransaction(request);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
