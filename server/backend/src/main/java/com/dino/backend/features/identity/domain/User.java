package com.dino.backend.features.identity.domain;

import com.dino.backend.features.identity.domain.model.UserRoleType;
import com.dino.backend.features.identity.domain.model.UserStatusType;
import com.dino.backend.features.shopping.domain.entity.Address;
import com.dino.backend.features.shopping.domain.entity.Cart;
import com.dino.backend.features.shopping.domain.entity.Order;
import com.dino.backend.shared.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.*;

@Entity
@Table(name = "users")
@DynamicInsert // ignore null-value attributes
@DynamicUpdate
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE user_id=?")
@SQLRestriction("is_deleted = false")
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "userId", updatable = false, nullable = false)
    String id;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    Token token;

    @OneToOne(mappedBy = "seller", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    Shop shop;

    @OneToOne(mappedBy = "buyer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    Cart cart;

    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    List<Address> addresses;

    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    List<Order> orders;

    String status;

    @Column(nullable = false)
    Set<String> roles;

    @Column(nullable = false, unique = true)
    String username;

    String password;

    @Column(unique = true)
    String email;

    @Column(unique = true)
    String phone;

    Boolean isEmailVerified;

    Boolean isPhoneVerified;

    String name;

    String photo;

    Date dob;

    String gender;

    public static User createSignupUser(User user, String hashPassword) {
        // EXP: the validation layer ensured:
        // - email: ensure the format
        // - password: ensure the size of 6
        User newUser = User.builder()
                .status(UserStatusType.LACK_INFO.toString())
                .roles(new HashSet<>(Collections.singletonList(UserRoleType.BUYER.toString())))
                .username("user" + System.currentTimeMillis())
                .password(hashPassword)
                .email(user.getEmail())
                .isEmailVerified(false)
                .name(user.getName())
                .build();

        Token newToken = Token.createToken(newUser);
        newUser.setToken(newToken);

        Cart newCart = Cart.createCart(newUser);
        newUser.setCart(newCart);

        return newUser;
    }
}
