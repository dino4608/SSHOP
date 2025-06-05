package com.dino.backend.features.ordering.api;

import com.dino.backend.features.ordering.application.ICheckoutService;
import com.dino.backend.features.ordering.application.ICheckoutServiceV3;
import com.dino.backend.features.ordering.application.model.CheckoutPreviewRes;
import com.dino.backend.features.ordering.application.model.PreviewCheckoutReq;
import com.dino.backend.features.ordering.application.model.PreviewCheckoutResV3;
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
        ICheckoutServiceV3 checkoutServiceV3;

        // QUERY //

        /**
         * previewCheckout
         * (Xem trước đơn hàng dựa trên danh sách cart item IDs được chọn)
         */
        @PostMapping("/preview")
        public ResponseEntity<PreviewCheckoutResV3> preview(
                @Valid @RequestBody PreviewCheckoutReq request,
                @AuthUser CurrentUser currentUser) {
            var checkout = this.checkoutServiceV3.previewCheckout(request, currentUser);
            return ResponseEntity.ok(checkout);
        }

        // TODO: HttpMessageNotReadableException: Required request body is missing

        // COMMAND //

        /**
         * createOrder
         * (Tạo đơn hàng thực tế)
         */
        @PostMapping("/order")
        public ResponseEntity<Object> createOrder(@AuthUser CurrentUser currentUser) {
            // Logic tạo đơn hàng thực tế
            return ResponseEntity.ok().build();
        }

        /**
         * confirm
         * (Xác nhận đơn hàng cuối cùng)
         */
        @PatchMapping("/confirm")
        public ResponseEntity<Object> confirm(@AuthUser CurrentUser currentUser) {
            // Logic
            return ResponseEntity.ok().build();
        }
    }
}
