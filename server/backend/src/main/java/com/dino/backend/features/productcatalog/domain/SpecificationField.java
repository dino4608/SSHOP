package com.dino.backend.features.productcatalog.domain;

import com.dino.backend.shared.model.BaseEntity;
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
@Table(name = "SpecificationFields")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "UPDATE specification_fields SET is_deleted = true WHERE category_id=?")
@SQLRestriction("is_deleted = false")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpecificationField extends BaseEntity {
    @Id
    String id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", updatable = false, nullable = false)
    Category category;

    String name;

    List<String> options;
}
