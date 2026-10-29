package com.example.trocd.loyality_service.entity;

import com.example.trocd.loyality_service.enums.BonusStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "bonuses",
        indexes = @Index(name = "idx_user_id", columnList = "userId"))
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Bonus {

    public Bonus(String userId, BonusStatus bonusStatus, BigDecimal discount) {
        this.userId = userId;
        this.bonusStatus = bonusStatus;
        this.discount = discount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id" , nullable = false)
    private String userId;

    @Column(name = "bonus_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BonusStatus bonusStatus;

    @Column(nullable = false, updatable = false)
    private BigDecimal discount;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public final boolean equals(Object o) {
        if(this == o) return true;
        if(o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ?((HibernateProxy) o).getHibernateLazyInitializer()
                .getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ?((HibernateProxy) this).getHibernateLazyInitializer()
                .getPersistentClass()
                : this.getClass();
        if(thisEffectiveClass != oEffectiveClass) return false;
        Bonus bonus = (Bonus) o;
        return getId() != null && Objects.equals(getId(), bonus.getId());
    }

    @Override
    public int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer()
                .getPersistentClass()
                .hashCode()
                : getClass().hashCode();

    }
}
