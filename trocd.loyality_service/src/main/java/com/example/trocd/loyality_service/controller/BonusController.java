package com.example.trocd.loyality_service.controller;

import com.example.trocd.loyality_service.dto.response.BonusResponse;
import com.example.trocd.loyality_service.repository.BonusRepository;
import com.example.trocd.loyality_service.service.BonusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bonuses")
public class BonusController {

    private final BonusService bonusService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<BonusResponse>> getUserBonuses(@PathVariable String userId){
        List<BonusResponse> bonusResponses = bonusService.getUserBonuses(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(bonusResponses);
    }

    @GetMapping("/{userId}/totalDiscount")
    public ResponseEntity<BigDecimal> getTotalUserDiscount(@PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(bonusService.getTheWholeUserDiscount(userId));
    }
}
