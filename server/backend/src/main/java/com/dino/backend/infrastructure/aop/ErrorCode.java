package com.dino.backend.infrastructure.aop;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    //SUCCESS CODE: 1//
    //SYSTEM 1000+//
    SYSTEM__UNHANDLED_EXCEPTION(1000, "Lỗi chưa được xử lý.", HttpStatus.INTERNAL_SERVER_ERROR),
    SYSTEM__DEVELOPING_FEATURE(1001, "The feature is still developing.", HttpStatus.INTERNAL_SERVER_ERROR),
    SYSTEM__UNIMPLEMENTED_FEATURE(1002, "The feature is still developing.", HttpStatus.INTERNAL_SERVER_ERROR),
    SYSTEM__KEY_UNSUPPORTED(1003, "The key is unsupported.", HttpStatus.INTERNAL_SERVER_ERROR),
    SYSTEM__METHOD_NOT_SUPPORTED(1004, "Method '%s' is not supported.", HttpStatus.INTERNAL_SERVER_ERROR),
    SYSTEM__ROUTE_NOT_SUPPORTED(1005, "Route '%s' not supported.", HttpStatus.INTERNAL_SERVER_ERROR),
    //ACCOUNT 1100+//
    USER__FIND_FAILED(1100, "Lấy người dùng thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    //SECURITY 1200+//
    SECURITY__UNAUTHENTICATED(1200, "Tài nguyên được bảo vệ.", HttpStatus.UNAUTHORIZED),
    SECURITY__UNAUTHORIZED(1201, "Tài nguyên bị cấm.", HttpStatus.FORBIDDEN),
    SECURITY__GET_CURRENT_USER_FAILED(1216, "Lấy người dùng hiện tại thất bại.", HttpStatus.INTERNAL_SERVER_ERROR),
    SECURITY__GEN_TOKEN_FAILED(1217, "Tạo token thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    OAUTH2__GET_GOOGLE_TOKEN_FAILED(1218, "Lấy access token của Google thất bại.", HttpStatus.INTERNAL_SERVER_ERROR),
    OAUTH2__GET_GOOGLE_USER_FAILED(1219, "Lấy thông tin người dùng của Google thất bại.", HttpStatus.INTERNAL_SERVER_ERROR),
    // AUTH 1300+ //
    AUTH__IDENTIFIER_REQUIRED(1300, "Email nên được cung cấp.", HttpStatus.BAD_REQUEST),
    AUTH__IDENTIFIER_NOT_FOUND(1301, "Email không tìm thấy.", HttpStatus.BAD_REQUEST),
    AUTH__IDENTIFIER_EXISTED(1302, "Email đã tồn tại.", HttpStatus.BAD_REQUEST),
    AUTH__PASSWORD_INVALID(1303, "Password không hợp lệ.", HttpStatus.BAD_REQUEST),
    AUTH__PASSWORD_MIN(1101, "Password nên có ít nhất 6 kí tự.", HttpStatus.BAD_REQUEST),
    AUTH__EMAIL_NOT_MATCHED(1102, "Email nên đúng định dạng.", HttpStatus.BAD_REQUEST),
    // AUTH__REFRESH_TOKEN_INVALID(1217, "Refresh token thì rỗng, trái phép, hoặc bị đánh cấp.", HttpStatus.BAD_REQUEST),
    // TOKEN 1300+ //
    TOKEN__FIND_FAILED(1300, "Lấy token thất bại.", HttpStatus.INTERNAL_SERVER_ERROR),
    TOKEN__UPDATE_FAILED(1302, "Cập nhật token thất bại.", HttpStatus.INTERNAL_SERVER_ERROR),
    //SHOP 1400+//

    //CATEGORY 1500+//
    CATEGORY__NOT_FOUND(1500, "Không tìm thấy ngành hàng.", HttpStatus.BAD_REQUEST),
    //PRODUCT 1600+//
    PRODUCT__NOT_FOUND(1600, "Không tìm thấy sản phẩm.", HttpStatus.BAD_REQUEST),
    //MEDIA 1700+//
    MEDIA__FILE_OUT_EXTENSIONS(1701, "File extensions should be pdf, jpg, jpeg, png, doc or docx.", HttpStatus.BAD_REQUEST),
    MEDIA__FILE_CREATE_DIRECTORY(1702, "An error occurred while creating a media directory.", HttpStatus.INTERNAL_SERVER_ERROR),
    MEDIA__FILE_CREATE_FOLDER(1703, "An error occurred while creating a media folder.", HttpStatus.INTERNAL_SERVER_ERROR),
    MEDIA__FILE_CREATE_PATH(1704, "An error occurred while creating a file path.", HttpStatus.INTERNAL_SERVER_ERROR),
    MEDIA__FILE_STREAM(1705, "An error occurred while stream the file.", HttpStatus.INTERNAL_SERVER_ERROR),
    MEDIA__FILE_EMPTY(1706, "File is empty. Please upload a file.", HttpStatus.BAD_REQUEST),
    //SKU 1800+//
    SKU__INVENTORY_NOT_EMPTY(1800, "The inventory should not be empty.", HttpStatus.BAD_REQUEST),
    SKU__NO_NOT_EMPTY(1801, "The no should not be empty.", HttpStatus.BAD_REQUEST),
    SKU__PRODUCT_COST_NOT_EMPTY(1802, "The product cost should not be empty.", HttpStatus.BAD_REQUEST),
    SKU__PRODUCT_COST_MIN(1803, "The product cost should be at least 1000 VND.", HttpStatus.BAD_REQUEST),
    SKU__RETAIL_PRICE_NOT_EMPTY(1804, "The retail price should not be empty.", HttpStatus.BAD_REQUEST),
    SKU__RETAIL_PRICE_MIN(1805, "The retail price should be at least 1000 VND.", HttpStatus.BAD_REQUEST),
    SKU__NOT_FOUND(1505, "The SKU is not found.", HttpStatus.BAD_REQUEST),
    SKU__CODE_UNIQUE(1506, "The SKU code already exists.", HttpStatus.BAD_REQUEST),
    //INVENTORY 1900+//
    INVENTORY__STOCKS_NOT_EMPTY(1900, "The stocks should not be empty.", HttpStatus.BAD_REQUEST),
    INVENTORY__STOCKS_MIN(1901, "The stocks should be at least 1.", HttpStatus.BAD_REQUEST),
    INVENTORY__SKU_NOT_FOUND(1902, "The sku is not found.", HttpStatus.BAD_REQUEST),
    //CART 2000+//
    CART__NOT_FOUND(2000, "The cart is not found.", HttpStatus.BAD_REQUEST),
    CART__QUANTITY_MIN(2001, "The quantity should be at least 1.", HttpStatus.BAD_REQUEST),
    CART__COUNT_MAX(2002, "The count should be at most 0.", HttpStatus.BAD_REQUEST),
    CART__ITEM_NOT_FOUND(2003, "The cart item is not found.", HttpStatus.BAD_REQUEST),
    //ADDRESS 2100+//
    ADDRESS__NOT_FOUND(2100, "The address is not found.", HttpStatus.BAD_REQUEST),
    //ORDER 2200+//
    ORDER__QUANTITY_MIN(2200, "The quantity should be at least 1.", HttpStatus.BAD_REQUEST),
    ORDER__QUANTITY_MAX(2201, "The quantity should not go beyond the available stock.", HttpStatus.BAD_REQUEST),
    ORDER__NOT_FOUND(2202, "The order is not found.", HttpStatus.BAD_REQUEST),
    ORDER__PAYMENT_METHOD_UNSUPPORTED(2203, "The payment method is unsupported.", HttpStatus.BAD_REQUEST);

    int code;
    String message;
    HttpStatusCode status;
}
