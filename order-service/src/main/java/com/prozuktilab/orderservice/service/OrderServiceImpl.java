package com.prozuktilab.orderservice.service;

import com.prozuktilab.orderservice.dto.OrderLineItemDto;
import com.prozuktilab.orderservice.dto.OrderRequest;
import com.prozuktilab.orderservice.model.Order;
import com.prozuktilab.orderservice.model.OrderLineItem;
import com.prozuktilab.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemList(orderRequest.getOrderLineItemDtoList()
                        .stream().map(this::dtoToEntity).toList())
                .build();
        orderRepository.save(order);
    }

    private OrderLineItem dtoToEntity(OrderLineItemDto orderLineItemDto) {
        return OrderLineItem.builder()
                .skuCode(orderLineItemDto.getSkuCode())
                .price(orderLineItemDto.getPrice())
                .quantity(orderLineItemDto.getQuantity())
                .build();
    }
}
