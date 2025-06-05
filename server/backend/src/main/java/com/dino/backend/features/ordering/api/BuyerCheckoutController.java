package com.dino.backend.features.ordering.api;

import com.dino.backend.features.ordering.application.ICheckoutService;
import com.dino.backend.features.ordering.application.model.CheckoutSnapshotRes;
import com.dino.backend.features.ordering.application.model.EstimateCheckoutReq;
import com.dino.backend.shared.api.annotation.AuthUser;
import com.dino.backend.shared.api.model.CurrentUser;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BuyerCheckoutController {

    // BuyerPublicCheckoutController //

    // BuyerPrivateCheckoutController //
    @RestController
    @RequestMapping("/api/v1/checkout")
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class BuyerPrivateCheckoutController {

        ICheckoutService checkoutService;

        // QUERY //

        /**
         * estimateCheckout
         * (preview checkout of CartItem list)
         */
        @PostMapping("/estimate")
        public ResponseEntity<Object> estimateCheckout(
                @Valid @RequestBody EstimateCheckoutReq request,
                @AuthUser CurrentUser currentUser) {
            var checkout = this.checkoutService.estimateCheckout(request, currentUser);
            return ResponseEntity.ok(checkout);
        }

        // COMMAND //

        /**
         * draftCheckout
         * (Tạo đơn hàng thực tế)
         */
        @PostMapping("/make")
        public ResponseEntity<Object> draftCheckout(@AuthUser CurrentUser currentUser) {
            // Logic tạo đơn hàng thực tế
            return ResponseEntity.ok().build();
        }

        /**
         * confirm
         * (Xác nhận đơn hàng cuối cùng)
         */
        @PatchMapping("/confirm")
        public ResponseEntity<Object> confirmCheckout(@AuthUser CurrentUser currentUser) {
            // Logic
            return ResponseEntity.ok().build();
        }
    }
}
