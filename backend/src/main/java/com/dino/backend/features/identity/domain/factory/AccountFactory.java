package com.dino.backend.features.identity.domain.factory;

import com.dino.backend.features.identity.domain.entity.Account;
import com.dino.backend.features.identity.domain.entity.Shop;
import com.dino.backend.features.identity.domain.entity.Token;
import com.dino.backend.infrastructure.aop.AppException;
import com.dino.backend.infrastructure.aop.ErrorCode;
import com.dino.backend.shared.utils.AppUtils;
import com.dino.backend.features.shopping.domain.entity.Cart;
import com.dino.backend.features.shopping.domain.factory.CartFactory;

import java.util.HashSet;
import java.util.Set;

public class AccountFactory {
    public static Account createAccount(Account account) {
        //status
        account.setStatus(Account.StatusType.LIVE.toString());
        //username
        if (AppUtils.isEmpty(account.getUsername())) {
            account.setUsername("user" + System.currentTimeMillis());
        }
        //password
        if (!account.getPassword().startsWith("$2a$")) //$2a$ present for the bcrypt algorithm
            throw new AppException(ErrorCode.ACCOUNT__NOT_HASH_PASSWORD);
        //dob
        if (AppUtils.isEmpty(account.getDob()))
            account.setStatus(Account.StatusType.LACK_INFO.toString());
        //role
        Set<String> roles = account.getRoles();
        if (AppUtils.isEmpty(roles))
            throw new AppException(ErrorCode.ACCOUNT__LACK_INFO);

        Set<Account.RoleType> roleTypes = new HashSet<>();
        roles.stream().parallel().forEach(role -> {
            try {
                Account.RoleType roleType = Account.RoleType.valueOf(role);
                roleTypes.add(roleType);
            } catch (IllegalArgumentException e) {
                throw new AppException(ErrorCode.SYSTEM__KEY_UNSUPPORTED);
            }
        });
        //token
        account.setToken(createToken(Token.builder().build(), account));
        //buyer
        if (roleTypes.contains(Account.RoleType.BUYER))
            account.setCart(CartFactory.createCart(Cart.builder().build(), account));
        //seller
        if (roleTypes.contains(Account.RoleType.SELLER))
            account.setShop(ShopFactory.createShop(Shop.builder().build(), account));
        //response
        return account;
    }

    public static Account updateAccount(Account account) {
        //status
        account.setStatus(Account.StatusType.LIVE.toString());
        //username
        if (AppUtils.isEmpty(account.getUsername())) {
            account.setUsername("user" + System.currentTimeMillis());
        }
        //password
        if (!account.getPassword().startsWith("$2a$")) //$2a$ present for the bcrypt algorithm
            throw new AppException(ErrorCode.ACCOUNT__NOT_HASH_PASSWORD);
        //dob *change
        if (AppUtils.isEmpty(account.getDob()))
            throw new AppException(ErrorCode.ACCOUNT__LACK_INFO);
        //role *change
        Set<String> roles = account.getRoles();
        if (AppUtils.isEmpty(roles))
            throw new AppException(ErrorCode.ACCOUNT__LACK_INFO);
        roles.stream().parallel().forEach(role -> {
            try {
                Account.RoleType roleType = Account.RoleType.valueOf(role);
            } catch (IllegalArgumentException e) {
                throw new AppException(ErrorCode.SYSTEM__KEY_UNSUPPORTED);
            }
        });
        //response
        return account;
    }

    public static Account responseAccount(Account account) {
        account.setPassword(null);
        account.setPhone(null);
        account.setEmail(null);
        return account;
    }

    private static Token createToken(Token token, Account account) {
        token.setAccount(account);
        return token;
    }
}
