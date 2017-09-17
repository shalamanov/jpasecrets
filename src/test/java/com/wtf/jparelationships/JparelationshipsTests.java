package com.wtf.jparelationships;

import com.wtf.jparelationships.order.OrderEntity;
import com.wtf.jparelationships.order.OrderLineItemEntity;
import com.wtf.jparelationships.order.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JparelationshipsTests {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void orderIsSaved() {

        String orderId = UUID.randomUUID().toString();

        OrderEntity orderEntity = new OrderEntity(orderId);

        OrderLineItemEntity orderLineItemEntity1 = new OrderLineItemEntity();
        orderLineItemEntity1.setName("Name 1");
        orderEntity.getOrderLineItemEntities().add(orderLineItemEntity1);

        OrderLineItemEntity orderLineItemEntity2 = new OrderLineItemEntity();
        orderLineItemEntity2.setName("Name 2");
        orderEntity.getOrderLineItemEntities().add(orderLineItemEntity2);

        OrderLineItemEntity orderLineItemEntity3 = new OrderLineItemEntity();
        orderLineItemEntity3.setName("Name 3");
        orderEntity.getOrderLineItemEntities().add(orderLineItemEntity3);

        orderRepository.save(orderEntity);

        OrderEntity orderToCheck = orderRepository.findOne(orderId);
        assertEquals("Size mismatch", 3, orderToCheck.getOrderLineItemEntities().size());
        assertNotNull("Name1 is NULL", orderToCheck.getOrderLineItemEntities().get(0).getName());
        assertNotNull("Name2 is NULL", orderToCheck.getOrderLineItemEntities().get(1).getName());
        assertNotNull("Name3 is NULL", orderToCheck.getOrderLineItemEntities().get(2).getName());
        //assertNotNull("Order is NULL", orderToCheck.getOrderLineItemEntities().get(0).getOrderEntity());
    }

    @Test
    public void orderIsChanged() {

        String orderId = UUID.randomUUID().toString();

        OrderEntity orderEntity = new OrderEntity(orderId);
        List<OrderLineItemEntity> orderLineItemEntities = new ArrayList<>();

        OrderLineItemEntity orderLineItemEntity1 = new OrderLineItemEntity();
        orderLineItemEntity1.setName("Name 1");
        orderEntity.getOrderLineItemEntities().add(orderLineItemEntity1);

        OrderLineItemEntity orderLineItemEntity2 = new OrderLineItemEntity();
        orderLineItemEntity2.setName("Name 2");
        orderEntity.getOrderLineItemEntities().add(orderLineItemEntity2);

        OrderLineItemEntity orderLineItemEntity3 = new OrderLineItemEntity();
        orderLineItemEntity3.setName("Name 3");
        orderEntity.getOrderLineItemEntities().add(orderLineItemEntity3);

        orderRepository.save(orderEntity);

        OrderEntity orderToChange = orderRepository.findOne(orderId);
        OrderLineItemEntity orderLineItemEntityToRemove = orderToChange.getOrderLineItemEntities().stream().filter(it -> it.getName().equals("Name 2")).findFirst().get();
        orderToChange.getOrderLineItemEntities().remove(orderLineItemEntityToRemove);
        orderRepository.save(orderToChange);

        OrderEntity orderToVerify = orderRepository.findOne(orderId);
        assertEquals("Size mismatch", 2, orderToVerify.getOrderLineItemEntities().size());
        assertTrue(orderToVerify.getOrderLineItemEntities().stream().noneMatch(it -> it.getName().equals("Name2")));
    }

    @Test
    public void orderLineItemsAreReplaced() {

        String orderId = UUID.randomUUID().toString();

        OrderEntity orderEntity = new OrderEntity(orderId);
        List<OrderLineItemEntity> orderLineItemEntities = new ArrayList<>();

        OrderLineItemEntity orderLineItemEntity1 = new OrderLineItemEntity();
        orderLineItemEntity1.setName("Name 1");
        orderEntity.getOrderLineItemEntities().add(orderLineItemEntity1);

        OrderLineItemEntity orderLineItemEntity2 = new OrderLineItemEntity();
        orderLineItemEntity2.setName("Name 2");
        orderEntity.getOrderLineItemEntities().add(orderLineItemEntity2);

        OrderLineItemEntity orderLineItemEntity3 = new OrderLineItemEntity();
        orderLineItemEntity3.setName("Name 3");
        orderEntity.getOrderLineItemEntities().add(orderLineItemEntity3);

        orderRepository.save(orderEntity);

        OrderEntity orderToChange = orderRepository.findOne(orderId);
        List<OrderLineItemEntity> replacement = new ArrayList<>();

        OrderLineItemEntity orderLineItemEntityR1 = new OrderLineItemEntity();
        orderLineItemEntityR1.setName("Name R1");
        replacement.add(orderLineItemEntityR1);

        OrderLineItemEntity orderLineItemEntityR2 = new OrderLineItemEntity();
        orderLineItemEntityR2.setName("Name R2");
        replacement.add(orderLineItemEntityR2);

        orderToChange.setOrderLineItemEntities(replacement);
        orderRepository.save(orderToChange);

        OrderEntity orderToVerify = orderRepository.findOne(orderId);
        assertEquals("Size mismatch", 2, orderToVerify.getOrderLineItemEntities().size());
        assertTrue(orderToVerify.getOrderLineItemEntities().stream().noneMatch(it -> it.getName().equals("Name1")));
        assertTrue(orderToVerify.getOrderLineItemEntities().stream().noneMatch(it -> it.getName().equals("Name2")));
        assertTrue(orderToVerify.getOrderLineItemEntities().stream().noneMatch(it -> it.getName().equals("Name3")));
    }

}
