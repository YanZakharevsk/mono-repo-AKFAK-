package com.example.trocd.loyality_service.repository;

import com.example.trocd.loyality_service.entity.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {

    List<Bonus> findAllByUserId(String userId);

    @Query(value = "SELECT SUM(discount) FROM bonuses WHERE user_id = :userId", nativeQuery = true)
    BigDecimal calculateTheWholeUserBonuses(String userId);
}
