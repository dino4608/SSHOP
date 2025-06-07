package com.dino.backend.features.ordering.domain.repository;

import com.dino.backend.features.ordering.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByIdIn(@NonNull List<Long> ids);
}