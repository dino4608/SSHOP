package com.dino.backend.features.promotion.domain;

import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.dino.backend.features.promotion.domain.model.PricingType;
import com.dino.backend.features.promotion.domain.model.ProgramStatusType;
import com.dino.backend.features.shop.domain.Shop;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "program_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "discount_programs")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "UPDATE discount_programs SET is_deleted = true WHERE discount_program_id=?")
@SQLRestriction("is_deleted = false")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class DiscountProgram extends Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "discount_program_id", updatable = false, nullable = false)
    String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_type")
    ProgramStatusType statusType;

    @Enumerated(EnumType.STRING)
    @Column(name = "pricing_type")
    PricingType pricingType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", updatable = false, nullable = false)
    @JsonIgnore
    Shop shop;

    @OneToMany(mappedBy = "discountProgram", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Discount> discounts;

    // getPriority //
    public abstract int getPriority();

    // isStatusActive //
    public boolean isStatusActive() {
        return this.statusType == ProgramStatusType.ONGOING;
    }

    // isActive //
    public boolean isActive() {
        return this.isPeriodActive() && this.isStatusActive();
    }

}