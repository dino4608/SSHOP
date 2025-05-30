package com.dino.backend.features.ordering.api;

import com.dino.backend.features.ordering.application.ICartService;
import com.dino.backend.features.ordering.application.model.CartItemAddReq;
import com.dino.backend.features.ordering.application.model.CartItemAddRes;
import com.dino.backend.features.ordering.application.model.CartItemRemoveReq;
import com.dino.backend.features.ordering.application.model.CartRes;
import com.dino.backend.infrastructure.web.annotation.AuthUser;
import com.dino.backend.infrastructure.web.model.CurrentUser;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuyerCartController {

    // BuyerPublicCartController //

    // BuyerPrivateCartController //
    @RestController
    @RequestMapping("/api/v1/carts")
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class BuyerPrivateCartController {

        ICartService cartService;

        // QUERY //

        // getCart //
        @GetMapping("/get")
        public ResponseEntity<CartRes> getCart(@AuthUser CurrentUser currentUser) {
            var cart = this.cartService.get(currentUser);
            return ResponseEntity.ok(cart);
        }

        // COMMAND //

        // addCartItem //
        @PostMapping("/items/add")
        public ResponseEntity<CartItemAddRes> addCartItem(
                @Valid @RequestBody CartItemAddReq request,
                @AuthUser CurrentUser currentUser) {
            var addedItem = this.cartService.addCartItem(request, currentUser);
            return ResponseEntity.ok(addedItem);
        }

        // updateQuantity //
        @PatchMapping("/quantity/update")
        public ResponseEntity<CartItemAddRes> updateQuantity(
                @Valid @RequestBody CartItemAddReq request,
                @AuthUser CurrentUser currentUser) {
            var updatedItem = this.cartService.updateQuantity(request, currentUser);
            return ResponseEntity.ok(updatedItem);
        }

        // removeCartItems //
        @DeleteMapping("/items/remove")
        public ResponseEntity<Void> removeCartItems(
                @Valid @RequestBody CartItemRemoveReq request,
                @AuthUser CurrentUser currentUser) {
            this.cartService.removeCartItems(request, currentUser);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            // TODO: return NO_CONTENT
        }
    }
}