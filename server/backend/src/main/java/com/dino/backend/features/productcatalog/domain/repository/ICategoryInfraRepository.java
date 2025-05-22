package com.dino.backend.features.productcatalog.domain.repository;

import com.dino.backend.features.productcatalog.domain.model.CategoryProjection;
import com.dino.backend.features.productcatalog.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ICategoryInfraRepository extends JpaRepository<Category, String>, JpaSpecificationExecutor<Category> {
    // READ //

    <T> List<T> findAllProjectedBy(Pageable pageable, Class<T> type); // #2

    Page<CategoryProjection> findAllProjectedBy(Pageable pageable); // #2

    <T> List<T> findAllProjectedBy(Sort sort, Class<T> type); // #1

    List<CategoryProjection> findAllProjectedBy(Sort sort); // #1

    List<CategoryProjection> findAllProjectedByOrderByPositionAsc(); // #1

    Optional<Category> findFirstByName(String name);

    Optional<Category> findFirstByNameAndIdNot(String name, String cateId);
}
