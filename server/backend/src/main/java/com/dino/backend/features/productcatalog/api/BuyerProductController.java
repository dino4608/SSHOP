package com.dino.backend.features.productcatalog.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dino.backend.features.productcatalog.application.IProductService;
import com.dino.backend.infrastructure.web.annotation.AuthUser;
import com.dino.backend.infrastructure.web.model.CurrentUser;

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

        // QUERY //

        // list //
        @GetMapping("/list")
        public ResponseEntity<Object> list(
                @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
            return ResponseEntity.ok(this.productService.list(pageable));
        }

        @GetMapping("/{id}")
        public ResponseEntity<Object> getById(@PathVariable String id, @AuthUser CurrentUser currentUser) {

            return ResponseEntity.ok(this.productService.getById(id, currentUser));
        }

    }
}
