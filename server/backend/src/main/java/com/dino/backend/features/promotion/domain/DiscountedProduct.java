package com.dino.backend.features.promotion.domain;

import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.util.CollectionUtils;

import com.dino.backend.features.productcatalog.domain.Product;
import com.dino.backend.features.promotion.domain.model.PriceType;
import com.dino.backend.infrastructure.web.model.CurrentUser;
import com.dino.backend.shared.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "discounted_products")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "UPDATE discounted_products SET is_deleted = true WHERE discounted_product_id=?")
@SQLRestriction("is_deleted = false")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiscountedProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "discounted_product_id", updatable = false, nullable = false)
    String id;

    Integer discountPercent;

    Integer dealPrice;

    Integer minDealPrice;

    Integer maxDealPrice;

    Integer totalLimit; // NULL is unlimited

    Integer buyerLimit;

    Integer usedCount;

    List<String> usedBuyerIds;

    @Enumerated(EnumType.STRING)
    @Column(name = "price_type", nullable = false)
    PriceType priceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", updatable = false, nullable = false)
    @JsonIgnore
    Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id", updatable = false, nullable = false)
    @JsonIgnore
    Discount discount;

    @OneToMany(mappedBy = "discountedProduct", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<DiscountedSku> discountedSkus;

    // isWithinTotalLimit //
    private boolean isWithinTotalLimit() {
        if (this.totalLimit == null || this.usedCount == null)
            return true;

        return this.usedCount < this.totalLimit;
    }

    // isWithinTotalLimit //
    private boolean isWithinBuyerLimit(CurrentUser currentUser) {
        if (this.buyerLimit == null || CollectionUtils.isEmpty(this.usedBuyerIds))
            return true;

        long count = this.usedBuyerIds.stream()
                .filter(id -> id.equals(currentUser.id()))
                .count();
        return count < this.buyerLimit;
    }

    // canApply //
    public boolean canApply(CurrentUser currentUser) {
        if (!this.isWithinTotalLimit() || !this.isWithinBuyerLimit(currentUser))
            return false;

        return this.discount.isActive();

        // TODO: Kiểm tra điều kiện áp dụng SKU (thông qua priceType, discountedSkus)
    }
}
