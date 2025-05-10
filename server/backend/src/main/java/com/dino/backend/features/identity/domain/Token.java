package com.dino.backend.features.identity.domain;

import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;
import com.dino.backend.shared.model.BaseEntity;
import com.dino.backend.shared.utils.Required;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
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
@SQLDelete(sql = "UPDATE tokens SET deleted = true WHERE user_id=?")
@SQLRestriction("deleted = false")
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    public static Token createToken(Token token, User user) {
        try {
            Required.notNull(token.getId());

            return Token.builder()
                    .id(null)
                    .user(user)
                    .refreshToken(null)
                    .refreshTokenExpiry(null)
                    .build();

        } catch (Exception e) {
            throw new AppException(ErrorCode.TOKEN__CREATE_FAILED);
        }
    }

    // NOTE:
    // Should set each property, because it changes clearly, doesn't depend on old state, is proper to DDD
    public static Token updateRefreshToken(Token token, String refreshToken, Instant refreshTokenExpiry) {
        try {
            Required.notNull(token.getId());

            return Token.builder()
                    .id(Required.notNull(token.getId()))
                    .user(token.user)
                    .refreshToken(refreshToken)
                    .refreshTokenExpiry(refreshTokenExpiry)
                    .createdAt(token.getCreatedAt())
                    .build();

        } catch (Exception e) {
            throw new AppException(ErrorCode.TOKEN__CREATE_FAILED);
        }
    }
}
