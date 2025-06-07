package com.dino.backend.features.ordering.domain.repository;

import com.dino.backend.features.identity.domain.User;
import com.dino.backend.features.ordering.domain.Order;
import com.dino.backend.features.ordering.domain.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByBuyerAndStatus(@NonNull User buyer, @NonNull OrderStatus status);

    List<Order> findByIdIn(@NonNull List<Long> ids);
}