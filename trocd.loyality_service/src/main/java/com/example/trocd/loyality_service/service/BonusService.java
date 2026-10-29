package com.example.trocd.loyality_service.service;

import com.example.trocd.loyality_service.dto.response.BonusResponse;
import com.example.trocd.loyality_service.entity.Bonus;
import com.example.trocd.loyality_service.enums.BonusStatus;
import com.example.trocd.loyality_service.enums.CurrencyMoney;
import com.example.trocd.loyality_service.kafka.dto.TransactionKafkaDto;
import com.example.trocd.loyality_service.repository.BonusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BonusService {

    private final BonusRepository bonusRepository;

    public Bonus createBonus(TransactionKafkaDto kafkaDto){
        BigDecimal convertingAmount = convertToUSD(kafkaDto.getAmount(), kafkaDto.getCurrency());
        BonusStatus status = chooseBonusStatus(kafkaDto.getUserId());
        BigDecimal discount = calculateBonus(convertingAmount, status);
        Bonus bonus = new Bonus(kafkaDto.getUserId(), status, discount);

        Bonus createdBonus = bonusRepository.save(bonus);

        return createdBonus;
    }

    public BigDecimal getTheWholeUserDiscount(String userId){
        BigDecimal result = bonusRepository.calculateTheWholeUserBonuses(userId);
        return result;
    }

    public List<BonusResponse> getUserBonuses(String userId){
        List<Bonus> userBonuses = bonusRepository.findAllByUserId(userId);
        List<BonusResponse> userBonusResponses = new ArrayList<>();

        for(Bonus trashcan: userBonuses){
            userBonusResponses.add(new BonusResponse(trashcan));
        }

        return userBonusResponses;
    }
    private BigDecimal convertToUSD(BigDecimal amount, CurrencyMoney currencyMoney){
        if(currencyMoney == CurrencyMoney.USD) return amount;
        if(currencyMoney == CurrencyMoney.EUR) return amount.multiply(new BigDecimal(1.1145));
        if(currencyMoney == CurrencyMoney.BLR) return amount.multiply(new BigDecimal(0.3395));
        if(currencyMoney == CurrencyMoney.RUB) return amount.multiply(new BigDecimal(0.0118));

        return null;
    }

    private BonusStatus chooseBonusStatus(String userId){
        BigDecimal amount = bonusRepository.calculateTheWholeUserBonuses(userId);
        BigDecimal initialAmount = new BigDecimal(30.00);
        BigDecimal  mediumAmount = new BigDecimal(60.00);
        BigDecimal premiumAmount = new BigDecimal(100.00);
        if(amount == null) return BonusStatus.BEGINNER;
        if(amount.compareTo(initialAmount) <  0){
            return BonusStatus.BEGINNER;
        }
        else if(amount.compareTo(mediumAmount) < 0){
            return BonusStatus.MEDIUM;
        }
        else if(amount.compareTo(premiumAmount) < 0 )
            return BonusStatus.PREMIUM;

        return BonusStatus.PREMIUM;
    }

    private BigDecimal calculateBonus(BigDecimal price,  BonusStatus status){
        BigDecimal bonus = new BigDecimal(0.0);
        if(status == BonusStatus.BEGINNER){
            bonus = price.multiply(new BigDecimal(0.03));
        }else if(status == BonusStatus.MEDIUM){
            bonus = price.multiply(new BigDecimal(0.05));
        }else if(status == BonusStatus.PREMIUM){
            bonus = price.multiply(new BigDecimal(0.08));
        }
        return bonus;
    }

}
