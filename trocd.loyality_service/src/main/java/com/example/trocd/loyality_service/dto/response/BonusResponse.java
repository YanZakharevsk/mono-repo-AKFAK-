package com.example.trocd.loyality_service.dto.response;

import com.example.trocd.loyality_service.entity.Bonus;
import com.example.trocd.loyality_service.enums.BonusStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BonusResponse {

    public BonusResponse(Bonus bonus){
        this.id = bonus.getId();
        this.userId = bonus.getUserId();
        this.bonusStatus = bonus.getBonusStatus();
        this.discount = bonus.getDiscount();
        this.createdAt = bonus.getCreatedAt();
        this.updatedAt = bonus.getUpdatedAt();
    }
    private Long id;
    private String userId;
    private BonusStatus bonusStatus;
    private BigDecimal discount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
