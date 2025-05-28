package com.dino.backend.features.identity.domain;

import java.time.Instant;
import java.util.Optional;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;
import com.dino.backend.shared.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

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
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    @JsonIgnore
    User user;

    @Column(columnDefinition = "VARCHAR(1000)")
    String refreshToken;

    Instant refreshTokenExpiry;

    public static Token createToken(User user) {
        var newToken = Token.builder().build();

        newToken.setUser(user); // REFERENCE

        return newToken;
    }

    public static Token updateRefreshToken(Token token, String refreshToken, Instant refreshTokenExpiry) {
        Optional.of(token.getId())
                .orElseThrow(() -> new AppException(ErrorCode.TOKEN__UPDATE_FAILED));

        Token newToken = Token.builder()
                .id(token.getId())
                .refreshToken(refreshToken)
                .refreshTokenExpiry(refreshTokenExpiry)
                .createdAt(token.getCreatedAt())
                .isDeleted(token.getIsDeleted())
                .build();

        newToken.setUser(User.builder().id(token.getId()).build()); // REFERENCE

        return newToken;
    }
}
