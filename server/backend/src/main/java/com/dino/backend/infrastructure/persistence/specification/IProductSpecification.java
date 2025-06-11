package com.dino.backend.infrastructure.persistence.specification;

import com.dino.backend.features.productcatalog.domain.Product;
import com.dino.backend.features.productcatalog.domain.model.ProductItemView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;

public interface IProductSpecification extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @NativeQuery("""
            WITH query AS (
              SELECT plainto_tsquery('english', :keyword) AS q
            )
            SELECT p.product_id, p.status, p.updated_at, p.name, p.thumb, p.retail_price, p.meta,
                   ts_rank(p.text_search_vector, query.q) AS rank
            FROM deal.public.products p, query
            WHERE p.text_search_vector @@ query.q
            ORDER BY rank DESC;
            """)
    List<ProductItemView> searchFullText(@NonNull @Param("keyword") String keyword);
}
