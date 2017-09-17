package com.wtf.jparelationships.order;

import org.hibernate.annotations.Cascade;
import org.hibernate.criterion.Order;

import javax.persistence.*;

@Entity
@Table(name = "order_line_items")
public class OrderLineItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = OrderEntity.class)
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private OrderEntity orderEntity;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderLineItemEntity that = (OrderLineItemEntity) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (orderEntity != null ? orderEntity.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

}
