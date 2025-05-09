package com.dino.backend.features.identity.domain;

import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;
import com.dino.backend.shared.model.BaseEntity;
import com.dino.backend.shared.utils.Required;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.Instant;

@Entity
@Table(name = "tokens")
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "UPDATE tokens SET is_deleted = true WHERE user_id=?")
@SQLRestriction("is_deleted = false")
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token extends BaseEntity {

    @Id
    String id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", updatable = false, nullable = false)
    @JsonIgnore
    User user;

    @Column(columnDefinition = "VARCHAR(1000)")
    String refreshToken;

    Instant refreshTokenExpiry;

    public static Token createToken(User user) {
        return Token.builder()
                .user(user)
                .build();
    }

    // NOTE:
    // Should set each property, because it changes clearly, doesn't depend on old state, is proper to DDD
    public static Token updateRefreshToken(Token token, String refreshToken, Instant refreshTokenExpiry) {
        try {
            Required.notNull(token.getId());

            return Token.builder()
                    .id(token.getId())
                    .user(User.builder().id(token.getId()).build())
                    .refreshToken(refreshToken)
                    .refreshTokenExpiry(refreshTokenExpiry)
                    .createdAt(token.getCreatedAt())
                    .isDeleted(token.getIsDeleted())
                    .build();

        } catch (Exception e) {
            throw new AppException(ErrorCode.TOKEN__UPDATE_FAILED);
        }
    }
}
