package com.dino.backend.features.promotion.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dino.backend.features.promotion.application.IDiscountService;
import com.dino.backend.infrastructure.web.annotation.AuthUser;
import com.dino.backend.infrastructure.web.model.CurrentUser;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
public class BuyerDiscountController {

    // PublicBuyerDiscountController //

    // PrivateBuyerDiscountController //
    @RestController
    @RequestMapping("/api/v1/discounts")
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class PrivateBuyerDiscountController {

        IDiscountService discountService;

        // QUERY //

        // getByProductId //
        @GetMapping("/{id}")
        public ResponseEntity<Object> getByProductId(@PathVariable String id, @AuthUser CurrentUser currentUser) {
            return ResponseEntity.ok(discountService.canApply(id, currentUser));
        }
    }
}
