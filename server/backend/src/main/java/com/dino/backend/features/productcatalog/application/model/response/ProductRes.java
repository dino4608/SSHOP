package com.dino.backend.features.productcatalog.application.model.response;

import com.dino.backend.features.productcatalog.domain.model.ProductSpecification;
import com.dino.backend.features.productcatalog.domain.model.ProductTierVariation;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRes {
    String id;
    String status;
    boolean inStock;
    String name;
    String description;
    String slug;
    String thumb;
    List<String> photos;
    String video;
    String sizeChart;
    Float basePrice;
    Float weight; //unit in grams (g)
    String location;
    ArrayList<ProductSpecification> attributes;
    ArrayList<ProductTierVariation> tierVariations;
}
