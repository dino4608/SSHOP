package com.dino.backend.features.productcatalog.api;

import com.dino.backend.features.productcatalog.application.IProductAppService;
import com.dino.backend.features.productcatalog.domain.model.ProductProjection;
import com.dino.backend.shared.model.PageRes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductBuyerController {

    // PublicProductBuyerController //
    @RestController
    @RequestMapping("/api/v1/public/products")
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class PublicProductBuyerController {

        IProductAppService productAppService;

        //LIST//
        @GetMapping("/list")
        public ResponseEntity<PageRes<ProductProjection>> list(
                @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
        ) {
            return ResponseEntity.ok(this.productAppService.list(pageable));
        }

    }
}
