package com.dino.backend.features.promotion.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@DiscriminatorValue("NEW_ARRIVAL")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "UPDATE discount_promotions SET is_deleted = true WHERE promotion_id=?")
@SQLRestriction("is_deleted = false")
@Getter
@Setter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewArrivalDiscount extends ProductDiscount {

    // max 3 months
    // buyerLimit 1
    // no discounted sku price

}