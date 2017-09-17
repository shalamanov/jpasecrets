package com.wtf.jparelationships;

import com.wtf.jparelationships.order.OrderEntity;
import com.wtf.jparelationships.order.OrderLineItemEntity;
import com.wtf.jparelationships.order.OrderLineItemRepository;
import com.wtf.jparelationships.order.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JparelationshipsTests {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderLineItemRepository orderLineItemRepository;

    @Test
    public void orderIsSaved() {

        String orderId = UUID.randomUUID().toString();

        OrderEntity orderEntity = new OrderEntity(orderId);
        List<OrderLineItemEntity> orderLineItemEntities = new ArrayList<>();

        OrderLineItemEntity orderLineItemEntity1 = new OrderLineItemEntity();
        orderLineItemEntity1.setName("Name 1");
        orderEntity.addOrderLineItemEntity(orderLineItemEntity1);

        OrderLineItemEntity orderLineItemEntity2 = new OrderLineItemEntity();
        orderLineItemEntity2.setName("Name 2");
        orderEntity.addOrderLineItemEntity(orderLineItemEntity2);

        OrderLineItemEntity orderLineItemEntity3 = new OrderLineItemEntity();
        orderLineItemEntity3.setName("Name 3");
        orderEntity.addOrderLineItemEntity(orderLineItemEntity3);

        orderRepository.save(orderEntity);

        OrderEntity orderToCheck = orderRepository.findOne(orderId);
        assertEquals("Size mismatch", 3, orderToCheck.getOrderLineItemEntities().size());
        assertNotNull("Name is NULL", orderToCheck.getOrderLineItemEntities().get(0).getName());
        assertNotNull("Order is NULL", orderToCheck.getOrderLineItemEntities().get(0).getOrderEntity());
    }

    @Test
    public void orderIsChanged() {

        String orderId = UUID.randomUUID().toString();

        OrderEntity orderEntity = new OrderEntity(orderId);
        List<OrderLineItemEntity> orderLineItemEntities = new ArrayList<>();

        OrderLineItemEntity orderLineItemEntity1 = new OrderLineItemEntity();
        orderLineItemEntity1.setName("Name 1");
        orderEntity.addOrderLineItemEntity(orderLineItemEntity1);

        OrderLineItemEntity orderLineItemEntity2 = new OrderLineItemEntity();
        orderLineItemEntity2.setName("Name 2");
        orderEntity.addOrderLineItemEntity(orderLineItemEntity2);

        OrderLineItemEntity orderLineItemEntity3 = new OrderLineItemEntity();
        orderLineItemEntity3.setName("Name 3");
        orderEntity.addOrderLineItemEntity(orderLineItemEntity3);

        orderRepository.save(orderEntity);

        OrderEntity orderToChange = orderRepository.findOne(orderId);
        OrderLineItemEntity orderLineItemEntityToRemove = orderLineItemRepository.findAllByOrderEntity(orderEntity).stream().filter(it -> it.getName().equals("Name 2")).findFirst().get();
        orderToChange.removeOrderLineItemEntity(orderLineItemEntityToRemove);
        orderRepository.save(orderToChange);

        OrderEntity orderToVerify = orderRepository.findOne(orderId);
        assertEquals("Size mismatch", 2, orderToVerify.getOrderLineItemEntities().size());
        assertEquals("order mismatch", orderToVerify, orderToVerify.getOrderLineItemEntities().get(0).getOrderEntity());
        assertEquals("order mismatch", orderToVerify, orderToVerify.getOrderLineItemEntities().get(1).getOrderEntity());
        assertTrue(orderToVerify.getOrderLineItemEntities().stream().noneMatch(it -> it.getName().equals("Name2")));
    }

}
