package com.dino.backend.features.identity.domain;

import com.dino.backend.features.identity.domain.model.UserRoleType;
import com.dino.backend.features.identity.domain.model.UserStatusType;
import com.dino.backend.shared.model.BaseEntity;
import com.dino.backend.features.shopping.domain.entity.Address;
import com.dino.backend.features.shopping.domain.entity.Cart;
import com.dino.backend.features.shopping.domain.entity.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.*;

@Entity
@Table(name = "users")
@DynamicInsert // ignore null-value attributes
@DynamicUpdate
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE user_id=?")
@SQLRestriction("deleted = false")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
        user.setToken(Token.createToken(new Token(), user));
        user.setCart(Cart.createCart(new Cart(), user));

        user.setUsername("user" + System.currentTimeMillis());
        user.setIsEmailVerified(false);
        user.setPassword(hashPassword);
        user.setRoles(new HashSet<>(Collections.singletonList(UserRoleType.BUYER.toString())));
        user.setStatus(UserStatusType.LACK_INFO.toString()); // EXP LACK_INFO: lack of dob and gender

        // EXP the validation layer
        // - email: ensure the format
        // - password: ensure the size of 6

        return user;
    }

    public static User responseUser(User user) {
        user.setId(null);
        user.setPassword(null);
        user.setRoles(null);
        user.setCreatedAt(null);
        user.setUpdatedAt(null);

        return user;
    }
}
