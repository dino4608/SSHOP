package com.dino.backend.features.ordering.api;

import com.dino.backend.features.ordering.application.model.ConfirmCheckoutReq;
import com.dino.backend.features.ordering.application.model.EstimateCheckoutReq;
import com.dino.backend.features.ordering.application.model.InitCheckoutReq;
import com.dino.backend.features.ordering.application.service.ICheckoutService;
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
         * startCheckout
         */
        @PostMapping("/start")
        public ResponseEntity<Object> startCheckout(
                @Valid @RequestBody InitCheckoutReq request,
                @AuthUser CurrentUser currentUser) {
            var checkout = this.checkoutService.startCheckout(request, currentUser);
            return ResponseEntity.ok(checkout);
        }

        /**
         * confirm
         * (end the checkout process)
         */
        @PatchMapping("/confirm")
        public ResponseEntity<Object> confirmCheckout(
                @Valid @RequestBody ConfirmCheckoutReq request,
                @AuthUser CurrentUser currentUser) {
            var checkout = this.checkoutService.confirmCheckout(request, currentUser);
            return ResponseEntity.ok(checkout);
        }
    }
}
