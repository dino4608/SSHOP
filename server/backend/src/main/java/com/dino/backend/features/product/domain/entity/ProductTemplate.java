package com.dino.backend.features.product.domain.entity;

import com.dino.backend.features.inventory.domain.entity.Sku;
import com.dino.backend.shared.model.BaseEntity;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;

@Entity
@Table(name = "ProductTemplates")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "UPDATE product_templates SET is_deleted = true WHERE category_id=?")
@SQLRestriction("is_deleted = false")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductTemplate extends BaseEntity {
    @Id
    String id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", updatable = false, nullable = false)
    Category category;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    Product.Attribute attribute;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    Sku.Specification specification;
}
