package com.dino.backend.features.ordering.application.model;

import java.util.List;

// Response DTO for API preview checkout
public record CheckoutPreviewResV2(
        Long cartId,
        CheckoutSummaryResV2 summary
) {

    // Các record lồng nhau cho OrderPreviewRes, ShopRes không còn cần thiết ở đây
    // vì chúng ta chỉ hiển thị tổng quan ở cấp độ cao nhất.
    // Tuy nhiên, để đảm bảo code biên dịch, chúng ta vẫn cần OrderPreviewRes nếu nó được dùng nội bộ
    // trong CheckoutServiceImpl để tính toán.
    // Nếu OrderPreviewRes chỉ là một cấu trúc tính toán nội bộ, nó có thể không cần là public record.
    // Nhưng vì nó đã được định nghĩa là public record trong phiên bản trước,
    // và để giữ tính nhất quán, tôi sẽ giữ nó ở đây nhưng không sử dụng nó ở top-level response.
    // Nếu bạn muốn loại bỏ hoàn toàn, bạn có thể di chuyển nó vào một lớp helper nội bộ.

    // Giữ lại OrderPreviewRes và ShopRes nếu chúng được sử dụng nội bộ để tính toán
    // hoặc nếu bạn có ý định tái sử dụng chúng cho các response chi tiết hơn trong tương lai.
    // Hiện tại, chúng không phải là một phần của response CheckoutPreviewRes trực tiếp.

    // Record đại diện cho một đơn hàng giả định (một nhóm cart item của cùng một shop)
    // (Đây là cấu trúc nội bộ được sử dụng để tính toán tổng, không phải là phần của response này)
    public record OrderPreviewRes(
            Long groupId, // ID của nhóm (CartGroup), thường là ID của CartItem mới nhất trong nhóm
            ShopRes shop, // Thông tin shop của đơn hàng này
            List<CartItemRes> cartItems, // Các cart item trong đơn hàng giả định này

            // Chi tiết giá cho đơn hàng giả định này
            Integer orderItemsPrice, // Tổng tiền hàng của đơn hàng này (tổng retail price)
            Integer orderDiscountAmount, // Tổng giảm giá của đơn hàng này

            // Phân tích chi tiết giảm giá cho đơn hàng này
            Integer sellerProductDiscount, // Giảm giá sản phẩm của người bán (đã được tính toán)
            Integer sellerCouponDiscount, // Giảm giá coupon của người bán (mặc định 0 - chưa thực thi)
            Integer platformDiscount, // Giảm giá của nền tảng/ ứng dụng (mặc định 0 - chưa thực thi)

            // Chi tiết vận chuyển cho đơn hàng này
            Integer shippingFee, // Phí vận chuyển cuối cùng của đơn hàng này
            Integer initialShippingFee, // Phí vận chuyển ban đầu (mặc định 36000)
            Integer shippingDiscount // Giảm phí vận chuyển (mặc định -36000)
    ) {
        // Record đại diện cho thông tin Shop
        public record ShopRes(
                Long id,
                String name) {
        }
    }
}
