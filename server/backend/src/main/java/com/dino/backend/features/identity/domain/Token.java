package com.dino.backend.features.identity.domain;

import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;
import com.dino.backend.shared.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    Instant refreshExpDate;

    public static Token createToken(Token token, User user) {
        token.setUser(user);
        return token;
    }

    public static Token updateRefreshToken(Token token, String REFRESH_TOKEN, Instant refreshExpDate) {
        if (token.getId() == null) {
            throw new AppException(ErrorCode.TOKEN__LACK_ID);
        }

        token.setRefreshToken(REFRESH_TOKEN);
        token.setRefreshExpDate(refreshExpDate);

        return token;
    }
}
