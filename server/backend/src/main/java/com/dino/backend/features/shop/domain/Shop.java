package com.dino.backend.features.shop.domain;

import com.dino.backend.features.identity.domain.User;
import com.dino.backend.features.productcatalog.domain.Product;
import com.dino.backend.shared.model.BaseEntity;
import com.dino.backend.features.ordering.domain.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Table(name = "shops")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "UPDATE shops SET is_deleted = true WHERE account_id=?")
@SQLRestriction("is_deleted = false")
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Shop extends BaseEntity {

    @Id
    String id;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "sellerId", updatable = false, nullable = false)
    @JsonIgnore
    @ToString.Exclude
    User seller;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    List<Product> products;

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    List<Order> orders;

    String status;

    String shopName;

    String shopCode;

    String shopLogo;

    String contactEmail;

    String contactPhone;

    String businessType;

    String sellerType;

    // location => address

    // stars => review

    // sales => product metrics

    // returning customers => product metrics

    // mall, vietnam => meta
}
