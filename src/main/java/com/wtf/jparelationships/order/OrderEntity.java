package com.wtf.jparelationships.order;

import org.hibernate.criterion.Order;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    private String id;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLineItemEntity> orderLineItemEntities = new ArrayList<>();

    OrderEntity() {
    }

    public OrderEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<OrderLineItemEntity> getOrderLineItemEntities() {
        return orderLineItemEntities;
    }

    public void addOrderLineItemEntity(OrderLineItemEntity orderLineItemEntity) {
        this.orderLineItemEntities.add(orderLineItemEntity);
        orderLineItemEntity.setOrderEntity(this);
    }

    public void removeOrderLineItemEntity(OrderLineItemEntity orderLineItemEntity) {
        orderLineItemEntities.remove(orderLineItemEntity);
        orderLineItemEntity.setOrderEntity(null);
    }
}

