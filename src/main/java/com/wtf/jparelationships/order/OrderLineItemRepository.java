package com.wtf.jparelationships.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineItemRepository extends JpaRepository<OrderLineItemEntity, Integer> {
    
    List<OrderLineItemEntity> findAllByOrderEntity(OrderEntity orderEntity);
}
