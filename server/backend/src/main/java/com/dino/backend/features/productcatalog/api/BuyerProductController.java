package com.dino.backend.features.productcatalog.api;

import com.dino.backend.features.productcatalog.application.model.ProductSearchParams;
import com.dino.backend.features.productcatalog.application.reader.IProductReader;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dino.backend.features.productcatalog.application.service.IProductService;
import com.dino.backend.shared.api.annotation.AuthUser;
import com.dino.backend.shared.api.model.CurrentUser;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
public class BuyerProductController {

    // BuyerPublicProductController //
    @RestController
    @RequestMapping("/api/v1/public/products")
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class BuyerPublicProductController {

        IProductService productService;
        IProductReader productReader;

        // QUERY //

        // listProduct //
        @GetMapping("/list")
        public ResponseEntity<Object> listProduct(
                @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
            return ResponseEntity.ok(this.productService.listProduct(pageable));
        }

        // searchProduct //
        @GetMapping("/search")
        public ResponseEntity<Object> searchProduct(
                @ModelAttribute ProductSearchParams params) {
            return ResponseEntity.ok(this.productReader.searchProduct(params));
        }

        // getProduct //
        @GetMapping("/{id}")
        public ResponseEntity<Object> getProduct(@PathVariable String id, @AuthUser CurrentUser currentUser) {

            return ResponseEntity.ok(this.productService.getProduct(id, currentUser));
        }

    }
}
